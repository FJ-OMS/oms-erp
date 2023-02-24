package org.skyer.autoconfigure.gateway;

import org.skyer.core.redis.RedisHelper;
import org.skyer.gateway.config.GatewayProperties;
import org.skyer.gateway.config.MaintainProperties;
import org.skyer.gateway.endpoint.MaintainEndpoint;
import org.skyer.gateway.filter.AvailableServiceFilter;
import org.skyer.gateway.filter.HeaderWrapperFilter;
import org.skyer.gateway.filter.IpCheckedFilter;
import org.skyer.gateway.filter.RedisBlackSetRepository;
import org.skyer.gateway.filter.RedisWhiteSetRepository;
import org.skyer.gateway.filter.XForwardedForFilter;
import org.skyer.gateway.filter.metric.CustomInMemoryHttpTraceRepository;
import org.skyer.gateway.filter.metric.MetricEndpoint;
import org.skyer.gateway.filter.metric.MetricRuleEndpoint;
import org.skyer.gateway.filter.metric.RequestCountRules;
import org.skyer.gateway.filter.metric.RequestCountTraceListener;
import org.skyer.gateway.filter.metric.RequestCounter;
import org.skyer.gateway.filter.metric.TraceListener;
import org.skyer.gateway.helper.api.reactive.ReactiveAuthenticationHelper;
import org.skyer.gateway.helper.config.GatewayHelperProperties;
import org.skyer.gateway.ratelimit.RateLimitConfiguration;
import org.skyer.gateway.route.DatabaseRouteDefinitionRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.Arrays;
import java.util.List;

/**
 * @author XCXCXCXCX
 * @date 2019/9/9
 * @project skyer-gateway
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@Configuration
@EnableAutoConfiguration(excludeName = {
        "org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration",
        "org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration",
        "org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration"})
@EnableConfigurationProperties({MaintainProperties.class, GatewayProperties.class})
public class WebConditionAutoConfiguration {

    @Bean
    public MaintainEndpoint maintainEndpoint(MaintainProperties maintainProperties) {
        return new MaintainEndpoint(maintainProperties);
    }

    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @Configuration
    @Import(RateLimitConfiguration.class)
    public static class GatewayConfig {

        /**
         * 声明GateWayHelperFilter
         *
         * @return 配置的GateWayHelperFilter
         */
        @Bean
        public org.skyer.gateway.filter.GateWayHelperFilter gateWayHelperFilter(
                        ReactiveAuthenticationHelper gatewayHelper) {
            return new org.skyer.gateway.filter.GateWayHelperFilter(gatewayHelper);
        }

        /**
         * 解决跨域问题
         *
         * @return 跨域声明
         */
        @Bean
        @Order(Integer.MIN_VALUE)
        public CorsWebFilter corsWebFilter(GatewayProperties properties) {
            org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource source =
                            new org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            for (String allowedOrigin : properties.getCors().getAllowedOrigins()) {
                config.addAllowedOrigin(allowedOrigin);
            }
            for (String allowedHeader : properties.getCors().getAllowedHeaders()) {
                config.addAllowedHeader(allowedHeader);
            }
            for (String allowedMethod : properties.getCors().getAllowedMethods()) {
                config.addAllowedMethod(allowedMethod);
            }
            config.setMaxAge(18000L);
            // 添加response暴露的header
            String[] responseHeader = {"date", "content-encoding", "server", "etag", "vary", "Cache-Control",
                    "Last-Modified", "content-type", "transfer-encoding", "connection", "x-application-context"};
            config.setExposedHeaders(Arrays.asList(responseHeader));
            source.registerCorsConfiguration("/**", config);
            return new CorsWebFilter(source);
        }

        @Bean
        public HeaderWrapperFilter headerWrapperFilter(GatewayHelperProperties gatewayHelperProperties) {
            return new HeaderWrapperFilter(gatewayHelperProperties);
        }

        @Bean
        public AvailableServiceFilter normalServiceFilter(MaintainProperties maintainProperties) {
            return new AvailableServiceFilter(maintainProperties);
        }

        @Bean
        public XForwardedForFilter xForwardedForFilter() {
            return new XForwardedForFilter();
        }

        @Bean
        public RouteDefinitionRepository routeDefinitionRepository(Environment environment,
                        DiscoveryClient discoveryClient) {
            return new DatabaseRouteDefinitionRepository(environment, discoveryClient);
        }

    }

    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @Configuration
    @ConditionalOnProperty(name = "skyer.request.counter.enable", havingValue = "true")
    public static class MetricConfig {

        // ======= metric start
        @Bean
        @ConditionalOnMissingBean
        public RequestCountRules requestCountRules(RedisHelper redisHelper) {
            RedisWhiteSetRepository redisWhiteSetRepository = new RedisWhiteSetRepository(redisHelper);
            RedisBlackSetRepository redisBlackSetRepository = new RedisBlackSetRepository(redisHelper);
            return new RequestCountRules(redisHelper, redisWhiteSetRepository, redisBlackSetRepository);
        }

        @Bean
        public RequestCounter requestCounter(RequestCountRules requestCountRules) {
            RequestCounter counter = new RequestCounter(requestCountRules);
            counter.start();
            return counter;
        }

        @Bean
        public RequestCountTraceListener requestCountTraceListener(RequestCountRules requestCountRules) {
            return new RequestCountTraceListener(requestCountRules);
        }

        @Bean
        public CustomInMemoryHttpTraceRepository inMemoryHttpTraceRepository(List<TraceListener> listeners) {
            return new CustomInMemoryHttpTraceRepository(listeners);
        }

        @Bean
        public IpCheckedFilter ipCheckedFilter(RequestCountRules requestCountRules) {
            return new IpCheckedFilter(requestCountRules);
        }
        // ======= metric end

        // ======= metric endpoint start
        @Bean
        public MetricEndpoint metricEndpoint(RequestCounter requestCounter) {
            return new MetricEndpoint(requestCounter);
        }

        @Bean
        public MetricRuleEndpoint metricRuleEndpoint(RequestCountRules requestCountRules) {
            return new MetricRuleEndpoint(requestCountRules);
        }
        // ======= metric endpoint end

    }

}
