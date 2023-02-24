package org.skyer.stock.infra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.skyer.common.SkyerService;
import org.skyer.stock.domain.vo.RpcGoodsResVO;
import org.skyer.stock.infra.feign.impl.GoodsSkuRemoteServiceImpl;

/**
 *
 * @description
 * @Author wusc
 * @create 2021/8/9 11:05 上午
 */
@FeignClient(value = SkyerService.Goods.NAME, fallback = GoodsSkuRemoteServiceImpl.class, path = "/v1/goods/sku")
public interface GoodsSkuRemoteService {

    /**
     * 根据sku列表查询商品
     * @param skuCodeList
     * @return
     */
    @PostMapping("/list/goods")
    ResponseEntity<List<RpcGoodsResVO>> queryRPCGoodsList(@RequestBody List<String> skuCodeList);
}
