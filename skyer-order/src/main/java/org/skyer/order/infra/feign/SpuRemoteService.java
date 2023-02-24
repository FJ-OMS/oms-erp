package org.skyer.order.infra.feign;

import org.skyer.common.SkyerService;
import org.skyer.order.infra.feign.impl.SpuRemoteServiceImpl;
import org.skyer.order.infra.feign.vo.GoodsPageResVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Primary
@FeignClient(value = SkyerService.Goods.NAME, fallback = SpuRemoteServiceImpl.class, path = "/v1/goods/manager")
public interface SpuRemoteService {
    @PostMapping("/goods-list-spu")
    ResponseEntity<List<GoodsPageResVO>> queryGoodsBySpuList(List<String> spuCodeList);
}
