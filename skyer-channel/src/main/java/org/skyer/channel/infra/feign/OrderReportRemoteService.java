package org.skyer.channel.infra.feign;

import org.skyer.channel.domain.vo.ChannelSaleOrderVO;
import org.skyer.channel.infra.feign.fallback.UserClientImpl;
import org.skyer.channel.infra.feign.impl.OrderReportRemoteServiceImpl;
import org.skyer.channel.infra.remote.api.dto.SaleOrderOrderApiDTO;
import org.skyer.common.SkyerService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@Primary
@FeignClient(value = SkyerService.Order.NAME, fallback = OrderReportRemoteServiceImpl.class, path = "/v1/order-report")
public interface OrderReportRemoteService {

    @PostMapping("/channel-sale-report")
    ResponseEntity<List<ChannelSaleOrderVO>> queryChannelOrderSaleReport(@RequestBody SaleOrderOrderApiDTO saleOrderOrderApiDTO);

}
