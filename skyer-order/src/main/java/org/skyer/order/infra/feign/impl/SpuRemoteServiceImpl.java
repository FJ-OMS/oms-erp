package org.skyer.order.infra.feign.impl;

import org.skyer.order.infra.feign.SpuRemoteService;
import org.skyer.order.infra.feign.vo.GoodsPageResVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpuRemoteServiceImpl implements SpuRemoteService {

    @Override
    public ResponseEntity<List<GoodsPageResVO>> queryGoodsBySpuList(List<String> spuCodeList) {
        throw new RuntimeException("调用商品服务获取spu失败");
    }
}
