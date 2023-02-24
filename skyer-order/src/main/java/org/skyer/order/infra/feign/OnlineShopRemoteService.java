package org.skyer.order.infra.feign;

import io.swagger.annotations.ApiParam;
import org.skyer.common.SkyerService;
import org.skyer.order.infra.feign.dto.OnlineShopDTO;
import org.skyer.order.infra.feign.impl.LovRemoteServiceImpl;
import org.skyer.order.infra.feign.impl.OnlineShopRemoteServiceImpl;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description
 * @Author wusc
 * @create 2022/1/18 4:06 下午
 */
@Primary
@FeignClient(value = SkyerService.Channel.NAME, fallback = OnlineShopRemoteServiceImpl.class, path = "/v1/online/shop")
public interface OnlineShopRemoteService {

    @GetMapping("/details")
    ResponseEntity<OnlineShopDTO> showDetails(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id);
}
