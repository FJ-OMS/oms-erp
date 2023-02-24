package org.skyer.order.infra.feign.impl;

import org.skyer.order.infra.feign.OnlineShopRemoteService;
import org.skyer.order.infra.feign.dto.OnlineShopDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @description
 * @Author wusc
 * @create 2022/1/18 4:07 下午
 */
@Component
public class OnlineShopRemoteServiceImpl implements OnlineShopRemoteService {


    @Override
    public ResponseEntity<OnlineShopDTO> showDetails(Long id) {
        return null;
    }
}
