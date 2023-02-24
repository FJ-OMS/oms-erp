package org.skyer.goods.infra.constant;

/**
 * 用于redis缓存key的定义
 * @description
 * @Author wusc
 * @create 2021/6/29 10:37 上午
 */
public class RedisKey {

    public static final String PREFIX = "sgoo_";

    /**
     * 类目key
     */
    public static final String CATEGORY_KEY = "category_";

    /**
     * 所有类目缓存key
     */
    public static final String CATEGORY_ALL = PREFIX + "category_all";
}
