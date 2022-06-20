package org.skyer.goods.infra.feign.impl;

import org.skyer.core.exception.CommonException;
import org.skyer.goods.domain.vo.OnlineShopVO;
import org.skyer.goods.infra.feign.OnlineShopRemoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2021/8/16 4:09 下午
 */
@Component
public class OnlineShopRemoteServiceImpl implements OnlineShopRemoteService {

    @Override
    public ResponseEntity<OnlineShopVO> showDetails(Long id) {
        throw new CommonException("查询网店调用有误");
    }
}
