package org.skyer.gateway.filter;

import org.skyer.core.redis.RedisHelper;

import java.util.Set;

/**
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2019/12/5 8:21 下午
 */
public class RedisWhiteSetRepository extends RedisSetRepository {

    private static final String WHITELIST_TEMPLATE_KEY =
                    MODULE_CONTEXT + SPLIT + "whitelist" + SPLIT + ID_PLACEHOLDER + SPLIT + VALUE;

    private static String getWhiteListKey(Long id) {
        return WHITELIST_TEMPLATE_KEY.replace(RedisSetRepository.ID_PLACEHOLDER, String.valueOf(id));
    }

    public RedisWhiteSetRepository(RedisHelper redisHelper) {
        super("whitelist", redisHelper);
    }

    public Set<String> getWhitelistById(Long id) {
        return getRedisHelper().setMembers(getWhiteListKey(id));
    }
}
