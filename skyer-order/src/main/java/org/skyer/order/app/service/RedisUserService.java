package org.skyer.order.app.service;

/**
 * 用户redis缓存服务类
 *
 * @author chenzz
 */
public interface RedisUserService {

    /**
     * 获取用户资料
     *
     * @param userId
     * @return
     */
    String getUserName(Long userId);


}
