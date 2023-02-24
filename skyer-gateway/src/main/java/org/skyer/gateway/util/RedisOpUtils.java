package org.skyer.gateway.util;

import org.skyer.core.redis.RedisHelper;
import org.skyer.gateway.filter.Query;

/**
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2019/12/6 1:47 下午
 */
public class RedisOpUtils {

    public static <T> T selectDbAndClear(RedisHelper redisHelper, int db, Query<T> query) {

        try {
            redisHelper.setCurrentDatabase(db);
            return query.get();
        } finally {
            redisHelper.clearCurrentDatabase();
        }

    }
}
