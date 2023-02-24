package org.skyer.order.app.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.skyer.common.SkyerCacheKey;
import org.skyer.core.redis.RedisHelper;
import org.skyer.order.app.service.RedisUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisUserServiceImpl implements RedisUserService {

    @Autowired
    private RedisHelper redisHelper;

    @Override
    public String getUserName(Long userId) {
        if (ObjectUtil.isNotNull(userId)) {
            String jsonText = redisHelper.hshGet(SkyerCacheKey.USER, userId.toString());
            if (StrUtil.isNotEmpty(jsonText)) {
                JSONObject jsonObject = JSONUtil.parseObj(jsonText);
                return jsonObject.getStr("realName", "-");
            }
        }
        return String.valueOf(userId);
    }
}
