package org.skyer.order.infra.feign;

import org.skyer.common.SkyerService;
import org.skyer.order.infra.feign.impl.GoodsStockRemoteServiceImpl;
import org.skyer.order.infra.feign.vo.SkuUsableStockVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author xurongfa 2022-04-24 16:20
 */
@Primary
@FeignClient(value = SkyerService.Stock.NAME, fallback = GoodsStockRemoteServiceImpl.class, path = "/v1/goods-stocks")
public interface GoodsStockRemoteService {
    /**
     * 查询sku可用库存
     * @param skuCodeList sku编码集合
     * @return sku对应的可用库存
     */
    @PostMapping("/stats-sku-usable-stock")
    ResponseEntity<List<SkuUsableStockVO>> statsSkuUsableStock(@RequestBody List<String> skuCodeList);
}
