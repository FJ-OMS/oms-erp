package org.skyer.gateway.ratelimit.limiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.cloud.gateway.filter.ratelimit.AbstractRateLimiter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Min;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2019/11/13 10:17 上午
 */
public class EnhancedRedisRateLimiter extends AbstractRateLimiter<EnhancedRedisRateLimiter.Config>
                implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnhancedRedisRateLimiter.class);

    public static final String REPLENISH_RATE_KEY = "replenishRate";
    public static final String BURST_CAPACITY_KEY = "burstCapacity";

    public static final String CONFIGURATION_PROPERTY_NAME = "redis-rate-limiter";
    public static final String REDIS_SCRIPT_NAME = "redisRequestRateLimiterScript";
    public static final String REMAINING_HEADER = "X-RateLimit-Remaining";
    public static final String REPLENISH_RATE_HEADER = "X-RateLimit-Replenish-Rate";
    public static final String BURST_CAPACITY_HEADER = "X-RateLimit-Burst-Capacity";

    private ReactiveRedisTemplate<String, String> redisTemplate;
    private RedisScript<List<Long>> script;
    private AtomicBoolean initialized = new AtomicBoolean(false);
    private EnhancedRedisRateLimiter.Config defaultConfig;

    // configuration properties
    /** Whether or not to include headers containing rate limiter information, defaults to true. */
    private boolean includeHeaders = true;

    /** The name of the header that returns number of remaining requests during the current second. */
    private String remainingHeader = REMAINING_HEADER;

    /** The name of the header that returns the replenish rate configuration. */
    private String replenishRateHeader = REPLENISH_RATE_HEADER;

    /** The name of the header that returns the burst capacity configuration. */
    private String burstCapacityHeader = BURST_CAPACITY_HEADER;

    public EnhancedRedisRateLimiter(ReactiveRedisTemplate<String, String> redisTemplate, RedisScript<List<Long>> script,
                    Validator validator) {
        super(EnhancedRedisRateLimiter.Config.class, CONFIGURATION_PROPERTY_NAME, validator);
        this.redisTemplate = redisTemplate;
        this.script = script;
        this.defaultConfig = new EnhancedRedisRateLimiter.Config();
        defaultConfig.setReplenishRate(Integer.MAX_VALUE);
        defaultConfig.setBurstCapacity(Integer.MAX_VALUE);
        initialized.compareAndSet(false, true);
    }

    @Override
    public Mono<Response> isAllowed(String routeId, String id) {
        if (!this.initialized.get()) {
            throw new IllegalStateException("RedisRateLimiter is not initialized");
        }

        EnhancedRedisRateLimiter.Config routeConfig = getConfig().getOrDefault(routeId, defaultConfig);

        if (routeConfig == null) {
            throw new IllegalArgumentException("No Configuration found for route " + routeId);
        }

        // How many requests per second do you want a user to be allowed to do?
        int replenishRate = getReplenishRate(routeConfig, id);

        // How much bursting do you want to allow?
        int burstCapacity = getBurstCapacity(routeConfig, id);

        try {
            List<String> keys = getKeys(id);


            // The arguments to the LUA script. time() returns unixtime in seconds.
            List<String> scriptArgs = Arrays.asList(replenishRate + "", burstCapacity + "",
                            Instant.now().getEpochSecond() + "", "1");
            // allowed, tokens_left = redis.eval(SCRIPT, keys, args)
            Flux<List<Long>> flux = this.redisTemplate.execute(this.script, keys, scriptArgs);
            // .log("redisratelimiter", Level.FINER);
            return flux.onErrorResume(throwable -> Flux.just(Arrays.asList(1L, -1L)))
                            .reduce(new ArrayList<Long>(), (longs, l) -> {
                                longs.addAll(l);
                                return longs;
                            }).map(results -> {
                                boolean allowed = results.get(0) == 1L;
                                Long tokensLeft = results.get(1);

                                Response response = new Response(allowed, getHeaders(routeConfig, id, tokensLeft));

                                if (LOGGER.isDebugEnabled()) {
                                    LOGGER.debug("response: " + response);
                                }
                                return response;
                            });
        } catch (Exception e) {
            /*
             * We don't want a hard dependency on Redis to allow traffic. Make sure to set an alert so you know
             * if this is happening too much. Stripe's observed failure rate is 0.01%.
             */
            LOGGER.error("Error determining if user allowed from redis", e);
        }
        return Mono.just(new Response(true, getHeaders(routeConfig, id, -1L)));
    }

    public Integer getReplenishRate(EnhancedRedisRateLimiter.Config config, String id) {
        Integer replenishRate = config.getReplenishRateMap().get(id);
        if (replenishRate == null) {
            replenishRate = config.getReplenishRate();
        }
        return replenishRate;
    }

    public Integer getBurstCapacity(EnhancedRedisRateLimiter.Config config, String id) {
        Integer burstCapacity = config.getBurstCapacityMap().get(id);
        if (burstCapacity == null) {
            burstCapacity = config.getBurstCapacity();
        }
        return burstCapacity;
    }

    public HashMap<String, String> getHeaders(EnhancedRedisRateLimiter.Config config, String id, Long tokensLeft) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(this.remainingHeader, tokensLeft.toString());
        headers.put(this.replenishRateHeader, config == null ? String.valueOf(Integer.MAX_VALUE)
                        : String.valueOf(getReplenishRate(config, id)));
        headers.put(this.burstCapacityHeader, config == null ? String.valueOf(Integer.MAX_VALUE)
                        : String.valueOf(getBurstCapacity(config, id)));
        return headers;
    }

    static List<String> getKeys(String id) {
        // use `{}` around keys to use Redis Key hash tags
        // this allows for using redis cluster

        // Make a unique key per user.
        String prefix = "request_rate_limiter.{" + id;

        // You need two Redis keys for Token Bucket.
        String tokenKey = prefix + "}.tokens";
        String timestampKey = prefix + "}.timestamp";
        return Arrays.asList(tokenKey, timestampKey);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (initialized.compareAndSet(false, true)) {
            this.redisTemplate = context.getBean("stringReactiveRedisTemplate", ReactiveRedisTemplate.class);
            this.script = context.getBean(REDIS_SCRIPT_NAME, RedisScript.class);
            if (context.getBeanNamesForType(Validator.class).length > 0) {
                this.setValidator(context.getBean(Validator.class));
            }
        }
    }

    @Validated
    public static class Config {

        @Min(1)
        private Integer replenishRate;

        @Min(1)
        private Integer burstCapacity = 1;

        /**
         * key: KeyResolver.resolve() value: config {replenishRate, burstCapacity}
         */
        private Map<String, Integer> replenishRateMap = new HashMap<>();
        private Map<String, Integer> burstCapacityMap = new HashMap<>();

        public Integer getReplenishRate() {
            return replenishRate;
        }

        public void setReplenishRate(Integer replenishRate) {
            this.replenishRate = replenishRate;
        }

        public Integer getBurstCapacity() {
            return burstCapacity;
        }

        public void setBurstCapacity(Integer burstCapacity) {
            this.burstCapacity = burstCapacity;
        }

        public Map<String, Integer> getReplenishRateMap() {
            return replenishRateMap;
        }

        public void setReplenishRateMap(Map<String, Integer> replenishRateMap) {
            this.replenishRateMap = replenishRateMap;
        }

        public Map<String, Integer> getBurstCapacityMap() {
            return burstCapacityMap;
        }

        public void setBurstCapacityMap(Map<String, Integer> burstCapacityMap) {
            this.burstCapacityMap = burstCapacityMap;
        }
    }

}
