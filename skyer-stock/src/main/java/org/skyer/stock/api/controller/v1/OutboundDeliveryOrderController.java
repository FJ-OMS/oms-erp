package org.skyer.stock.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.stock.api.dto.OutboundDeliveryOrderPageDTO;
import org.skyer.stock.config.StockSwaggerApiConfig;
import org.skyer.stock.domain.service.OutboundDeliveryOrderService;
import org.skyer.stock.domain.vo.OutboundDeliveryOrderInfoVO;
import org.skyer.stock.domain.vo.OutboundDeliveryOrderListVO;
import org.skyer.swagger.annotation.Permission;

/**
 * @author xurongfa 2022-02-22 11:19
 */
@Api(tags = StockSwaggerApiConfig.OUTBOUND_DELIVERY_ORDER_MANAGE)
@RestController("outboundDeliveryOrderController.v1")
@RequestMapping("/v1/outbound/delivery/order")
public class OutboundDeliveryOrderController {
    @Autowired
    private OutboundDeliveryOrderService outboundDeliveryOrderService;

    @ApiOperation(value = "出库通知单列表")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/page")
    public ResponseEntity<Page<OutboundDeliveryOrderListVO>> page(@RequestBody OutboundDeliveryOrderPageDTO dto) {
        Page<OutboundDeliveryOrderListVO> page = outboundDeliveryOrderService.getPage(dto);
        return Results.success(page);
    }

    @ApiOperation(value = "出库通知单详情")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/detail/{id}")
    public ResponseEntity<OutboundDeliveryOrderInfoVO> detail(@PathVariable @Encrypt Long id) {
        OutboundDeliveryOrderInfoVO detail = outboundDeliveryOrderService.detail(id);
        return Results.success(detail);
    }

}
