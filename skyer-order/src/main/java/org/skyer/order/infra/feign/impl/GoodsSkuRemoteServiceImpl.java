package org.skyer.order.infra.feign.impl;

import org.skyer.order.infra.feign.GoodsSkuRemoteService;
import org.skyer.order.infra.feign.vo.RpcGoodsResVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2022/1/19 1:18 下午
 */
@Component
public class GoodsSkuRemoteServiceImpl implements GoodsSkuRemoteService {

    @Override
    public ResponseEntity<List<RpcGoodsResVO>> queryOrderRpcList(List<String> skuCodeList) {
        return null;
    }

    @Override
    public ResponseEntity<List<RpcGoodsResVO>> getMealsGoodsList(List<String> skuCodeList) {
        return null;
    }
}
