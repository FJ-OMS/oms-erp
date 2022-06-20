package org.skyer.stock.infra.feign.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import org.skyer.core.exception.CommonException;
import org.skyer.stock.domain.vo.RpcGoodsResVO;
import org.skyer.stock.infra.feign.GoodsSkuRemoteService;

/**
 * @description
 * @Author wusc
 * @create 2021/8/9 11:13 上午
 */
@Component
public class GoodsSkuRemoteServiceImpl implements GoodsSkuRemoteService {
    @Override
    public ResponseEntity<List<RpcGoodsResVO>> queryRPCGoodsList(List<String> skuCodeList) {
        throw new CommonException(
                "查询商品列表远程调用失败");
    }
}
