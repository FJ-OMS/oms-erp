package org.skyer.order.infra.feign;

import org.skyer.common.SkyerService;
import org.skyer.order.infra.feign.impl.GoodsSkuRemoteServiceImpl;
import org.skyer.order.infra.feign.impl.LovRemoteServiceImpl;
import org.skyer.order.infra.feign.vo.RpcGoodsResVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2022/1/19 1:15 下午
 */
@Primary
@FeignClient(value = SkyerService.Goods.NAME, fallback = GoodsSkuRemoteServiceImpl.class, path = "/v1/goods/sku")
public interface GoodsSkuRemoteService {

    /**
     * 远程调用商品中心生成商品快照
     * @param skuCodeList
     * @return
     */
    @PostMapping("/order/sku/list")
    ResponseEntity<List<RpcGoodsResVO>> queryOrderRpcList(@RequestBody List<String> skuCodeList);

    @PostMapping("/meals-goods-list")
    ResponseEntity<List<RpcGoodsResVO>> getMealsGoodsList(@RequestBody List<String> skuCodeList);
}
