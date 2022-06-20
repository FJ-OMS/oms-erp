package org.skyer.goods.infra.feign;

import org.skyer.common.SkyerService;
import org.skyer.goods.domain.vo.OnlineShopVO;
import org.skyer.goods.infra.feign.impl.OnlineShopRemoteServiceImpl;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2021/8/16 3:57 下午
 */
@FeignClient(value = SkyerService.Channel.NAME, fallback = OnlineShopRemoteServiceImpl.class, path = "/v1/online/shop")
public interface OnlineShopRemoteService {


    /**
     * 根据sku列表查询商品
     * @param
     * @return
     */
    @GetMapping("/details")
    ResponseEntity<OnlineShopVO> showDetails(@RequestParam(value = "id") @Encrypt Long id);
}
