package org.skyer.gateway.filter;

import org.skyer.core.redis.RedisHelper;
import org.skyer.gateway.common.StringSetRepository;
import org.skyer.gateway.util.RedisOpUtils;

import java.util.Set;

/**
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2019/12/5 8:03 下午
 */
public abstract class RedisSetRepository implements StringSetRepository {

    protected static final String MODULE_CONTEXT = "hadm";
    protected static final String SPLIT = ":";
    protected static final String ENABLE = "enable";
    protected static final String VALUE = "value";
    protected static final String ID_PLACEHOLDER = "{id}";

    private static final int SELECT_DB = 1;

    private String keyPrefix;

    private RedisHelper redisHelper;

    public RedisSetRepository(String mode, RedisHelper redisHelper) {
        this.keyPrefix = MODULE_CONTEXT + SPLIT + mode + SPLIT;
        this.redisHelper = redisHelper;
    }

    private <T> T selectDbAndClear(RedisHelper redisHelper, Query<T> query) {
        return RedisOpUtils.selectDbAndClear(redisHelper, SELECT_DB, query);
    }

    @Override
    public boolean isEnable() {
        return selectDbAndClear(redisHelper, () -> Boolean.parseBoolean(redisHelper.strGet(getKeyPrefix() + ENABLE)));
    }

    @Override
    public Set<String> get() {
        return selectDbAndClear(redisHelper, () -> redisHelper.setMembers(getKeyPrefix() + VALUE));
    }

    @Override
    public boolean contains(String key) {
        return selectDbAndClear(redisHelper, () -> redisHelper.setIsmember(getKeyPrefix() + VALUE, key));
    }

    protected String getKeyPrefix() {
        return keyPrefix;
    }

    protected RedisHelper getRedisHelper() {
        return redisHelper;
    }
}
