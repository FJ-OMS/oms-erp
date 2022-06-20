package org.skyer.channel.infra.feign.fallback;

import org.skyer.channel.infra.feign.UserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 用户信息查询失败回调
 * @author zhanglb
 */
@Component
public class UserClientImpl implements UserClient {
    private static final Logger logger = LoggerFactory.getLogger(UserClientImpl.class);

    @Override
    public ResponseEntity companyUser(List<Long> companyIds) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
