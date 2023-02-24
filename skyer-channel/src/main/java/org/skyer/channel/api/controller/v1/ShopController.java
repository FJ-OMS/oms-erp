package org.skyer.channel.api.controller.v1;

import feign.ResponseMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.skyer.channel.api.dto.ChannelSaleOrderDTO;
import org.skyer.channel.domain.vo.ChannelSaleOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.skyer.channel.api.dto.QueryShopListDTO;
import org.skyer.channel.config.ChannelSwaggerApiConfig;
import org.skyer.channel.domain.service.saga.ShopService;
import org.skyer.channel.domain.vo.ShopListVO;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.swagger.annotation.Permission;

import java.util.List;

@RestController("shopController.v1")
@RequestMapping(value = "/v1/shop")
@Api(tags = ChannelSwaggerApiConfig.SHOP_MANAGE)
public class ShopController {
    @Autowired
    private ShopService shopService;

    @ApiOperation("查询所有店铺")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/page")
    public ResponseEntity<Page<ShopListVO>> queryShop(@RequestBody QueryShopListDTO dto) {
        return Results.success(shopService.queryShop(dto));
    }

    @ApiOperation("店铺销售表")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/channel-order-report")
    public ResponseEntity<List<ChannelSaleOrderVO>> queryChannelOrderSaleReport(@RequestBody ChannelSaleOrderDTO dto) {
        List<ChannelSaleOrderVO> channelSaleOrderVOS = shopService.queryChannelOrderSaleReport(dto);
        return Results.success(channelSaleOrderVOS);
    }
}
