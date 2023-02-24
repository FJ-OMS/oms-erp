package org.skyer.order.infra.feign.impl;

import org.skyer.core.exception.CommonException;
import org.skyer.order.infra.feign.GoodsStockRemoteService;
import org.skyer.order.infra.feign.vo.SkuUsableStockVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xurongfa 2022-04-24 16:24
 */
@Component
public class GoodsStockRemoteServiceImpl implements GoodsStockRemoteService {
    @Override
    public ResponseEntity<List<SkuUsableStockVO>> statsSkuUsableStock(List<String> skuCodeList) {
        throw new CommonException("调用库存获取sku可用库存异常");
    }
}
