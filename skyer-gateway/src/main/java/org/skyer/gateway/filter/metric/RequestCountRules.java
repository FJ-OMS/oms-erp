package org.skyer.gateway.filter.metric;

import org.skyer.core.redis.RedisHelper;
import org.skyer.gateway.filter.RedisBlackSetRepository;
import org.skyer.gateway.filter.RedisWhiteSetRepository;
import org.skyer.gateway.util.RedisOpUtils;
import org.skyer.gateway.util.RequestCountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * redis storage
 * <p>
 * id key: hadm:api:monitor:ids value: 1,2
 * <p>
 * pattern key: hadm:api:monitor:patterns value: /hadm/**,/hiam/**
 * <p>
 * time_window_size key: hadm:api:monitor:windows value: 5,5
 *
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2019/12/6 11:25 上午
 */
public final class RequestCountRules {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestCountRules.class);

    private static final String MODULE_CONTEXT = "hadm";
    private static final String SPLIT = ":";
    private static final String SUBMODULE_NAME = "api:monitor";
    private static final String IDS_KEY = "ids";
    private static final String PATTERNS_KEY = "patterns";
    private static final String TIME_WINDOW_SIZES_KEY = "windows";
    private static final String BLACKLIST_THRESHOLD_KEY = "blacklist-threshold";

    private String keyPrefix;

    private volatile List<Long> ids = new ArrayList<>();

    private volatile List<String> patterns = new ArrayList<>();

    private volatile List<Integer> timeWindowSizes = new ArrayList<>();

    private volatile List<Set<String>> blacklist = new ArrayList<>();
    private volatile List<Set<String>> whitelist = new ArrayList<>();

    private volatile Map<Long, Integer> blacklistThresholdMap = new HashMap<>();

    /**
     * key: {id}:{uri}:{ip}
     */
    private Map<String, TimeWindow> timeWindowsMap = new ConcurrentHashMap<>();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private RedisHelper redisHelper;
    private RedisWhiteSetRepository whiteSetRepository;
    private RedisBlackSetRepository blackSetRepository;

    public RequestCountRules(RedisHelper redisHelper, RedisWhiteSetRepository whiteSetRepository,
                    RedisBlackSetRepository blackSetRepository) {
        this.keyPrefix = MODULE_CONTEXT + SPLIT + SUBMODULE_NAME + SPLIT;
        this.redisHelper = redisHelper;
        this.whiteSetRepository = whiteSetRepository;
        this.blackSetRepository = blackSetRepository;
        refreshRules();
    }

    protected List<Long> refreshRules() {
        List<Long> tmpIds = RedisOpUtils.selectDbAndClear(redisHelper, RequestCountTraceListener.SELECT_DB,
                        () -> redisHelper.lstAll(keyPrefix + IDS_KEY).stream().map(Long::parseLong)
                                        .collect(Collectors.toList()));
        List<String> tmpPatterns = RedisOpUtils.selectDbAndClear(redisHelper, RequestCountTraceListener.SELECT_DB,
                        () -> redisHelper.lstAll(keyPrefix + PATTERNS_KEY));
        List<Integer> tmpWindows = RedisOpUtils.selectDbAndClear(redisHelper, RequestCountTraceListener.SELECT_DB,
                        () -> redisHelper.lstAll(keyPrefix + TIME_WINDOW_SIZES_KEY).stream().map(Integer::parseInt)
                                        .collect(Collectors.toList()));
        Map<String, String> tmpMap = RedisOpUtils.selectDbAndClear(redisHelper, RequestCountTraceListener.SELECT_DB,
                        () -> redisHelper.hshGetAll(keyPrefix + BLACKLIST_THRESHOLD_KEY));
        List<Set<String>> tmpBlacklist =
                        RedisOpUtils.selectDbAndClear(redisHelper, RequestCountTraceListener.SELECT_DB, () -> {
                            if (!CollectionUtils.isEmpty(tmpIds)) {
                                List<Set<String>> tmp = new ArrayList<>();
                                for (Long id : tmpIds) {
                                    Set<String> tmpSet = blackSetRepository.getBlacklistById(id);
                                    tmp.add(tmpSet);
                                }
                                return tmp;
                            }
                            return Collections.emptyList();
                        });
        List<Set<String>> tmpWhitelist =
                        RedisOpUtils.selectDbAndClear(redisHelper, RequestCountTraceListener.SELECT_DB, () -> {
                            if (!CollectionUtils.isEmpty(tmpIds)) {
                                List<Set<String>> tmp = new ArrayList<>();
                                for (Long id : tmpIds) {
                                    Set<String> tmpSet = whiteSetRepository.getWhitelistById(id);
                                    tmp.add(tmpSet);
                                }
                                return tmp;
                            }
                            return Collections.emptyList();
                        });

        Map<Long, Integer> tmpBlacklistThresholdMap = new HashMap<>(8);
        for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
            tmpBlacklistThresholdMap.put(Long.parseLong(entry.getKey()), Integer.valueOf(entry.getValue()));
        }

        // 保证原子性
        synchronized (this) {
            ids = tmpIds;
            patterns = tmpPatterns;
            timeWindowSizes = tmpWindows;
            blacklist = tmpBlacklist;
            whitelist = tmpWhitelist;
            blacklistThresholdMap = tmpBlacklistThresholdMap;
        }
        return ids;
    }

    public int match(String uri) {
        for (int i = 0; i < patterns.size(); i++) {
            if (antPathMatcher.match(patterns.get(i), uri)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据uri匹配找到相应的黑名单列表
     * 
     * @param uri
     * @return
     */
    public Set<String> getBlacklist(String uri) {
        Set<String> tmpBlacklist = new HashSet<>();
        for (int i = 0; i < patterns.size(); i++) {
            if (antPathMatcher.match(patterns.get(i), uri)) {
                Set<String> tmp = this.blacklist.get(i);
                if (!CollectionUtils.isEmpty(tmp)) {
                    tmpBlacklist.addAll(tmp);
                }
            }
        }
        return tmpBlacklist;
    }

    /**
     * 根据uri匹配找到相应的白名单列表
     * 
     * @param uri
     * @return
     */
    public Set<String> getWhitelist(String uri) {
        Set<String> tmpWhitelist = new HashSet<>();
        for (int i = 0; i < patterns.size(); i++) {
            if (antPathMatcher.match(patterns.get(i), uri)) {
                Set<String> tmp = this.whitelist.get(i);
                if (!CollectionUtils.isEmpty(tmp)) {
                    tmpWhitelist.addAll(tmp);
                }
            }
        }
        return tmpWhitelist;
    }

    public TimeWindow getTimeWindow(int index, String uri, String ip) {
        Long id;
        Integer size;
        // 保证原子性
        synchronized (this) {
            id = ids.get(index);
            size = timeWindowSizes.get(index);
        }
        if (id == null || size == null) {
            return null;
        }
        String key = RequestCountUtils.generateKey(id, uri, ip);
        TimeWindow window = timeWindowsMap.get(key);
        if (window == null) {
            window = new TimeWindow(size);
            timeWindowsMap.putIfAbsent(key, window);
        }
        return timeWindowsMap.get(key);
    }

    public Map<String, TimeWindow> getTimeWindowsMap() {
        return Collections.unmodifiableMap(timeWindowsMap);
    }

    public void removeTimeWindows(List<String> removeKeys) {
        for (String key : removeKeys) {
            timeWindowsMap.remove(key);
        }
    }

    /**
     * 拉黑
     * 
     * @param key
     * @param requestTimes
     * @param failRequestTimes
     */
    public void blacklistIfNeed(String key, int requestTimes, int failRequestTimes) {
        Long id = Long.valueOf(key.substring(0, key.indexOf(':')));
        String ip = key.substring(key.lastIndexOf(":") + 1);
        Integer threshold = blacklistThresholdMap.get(id);
        if (threshold != null && requestTimes > threshold) {
            blacklist(id, ip);
        }
    }

    private void blacklist(Long id, String ip) {
        LOGGER.debug("ip[{}] enter blacklist", ip);
        blackSetRepository.blacklist(id, ip);
    }

    public boolean containKey(String key) {
        Long id = RequestCountUtils.resolveId(key);
        return ids.contains(id);
    }

}
