package org.skyer.gateway.filter;

import org.skyer.core.redis.RedisHelper;

import java.util.Set;

/**
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2019/12/5 8:22 下午
 */
public class RedisBlackSetRepository extends RedisSetRepository {

    private static final String BLACKLIST_TEMPLATE_KEY =
                    MODULE_CONTEXT + SPLIT + "blacklist" + SPLIT + ID_PLACEHOLDER + SPLIT + VALUE;

    private static String getBlackListKey(Long id) {
        return BLACKLIST_TEMPLATE_KEY.replace(RedisSetRepository.ID_PLACEHOLDER, String.valueOf(id));
    }

    public RedisBlackSetRepository(RedisHelper redisHelper) {
        super("blacklist", redisHelper);
    }

    public Set<String> getBlacklistById(Long id) {
        return getRedisHelper().setMembers(getBlackListKey(id));
    }

    public void blacklist(Long id, String ip) {
        getRedisHelper().setAdd(getBlackListKey(id), new String[] {ip});
    }
}
