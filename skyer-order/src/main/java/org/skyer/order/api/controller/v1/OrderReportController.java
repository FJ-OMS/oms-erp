package org.skyer.order.api.controller.v1;


import io.swagger.annotations.ApiOperation;
import oracle.jdbc.proxy.annotation.Post;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.order.api.dto.OrderItemDTO;
import org.skyer.order.api.dto.OrderSaleDTO;
import org.skyer.order.api.dto.SaleItemReportDTO;
import org.skyer.order.api.vo.OrderSaleDateVO;
import org.skyer.order.api.vo.OrderSaleReportVO;
import org.skyer.order.api.vo.SaleItemReportVO;
import org.skyer.order.app.dto.SaleOrderOrderApiDTO;
import org.skyer.order.app.service.OrderReportService;
import org.skyer.order.app.vo.ChannelSaleOrderVO;
import org.skyer.swagger.annotation.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("orderLogisticsSiteController.v1")
@RequestMapping("/v1/order-report")
public class OrderReportController {
    @Autowired
    private OrderReportService orderReportService;

    @ApiOperation(value = "订单销售统计表")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping("/order-sale-report")
    public ResponseEntity<List<OrderSaleReportVO>> queryOrderSaleReport(@RequestBody OrderSaleDTO orderSaleDTO) {
        List<OrderSaleReportVO> orderSaleReportVOS = orderReportService.queryOrderSaleReport(orderSaleDTO);
        return Results.success(orderSaleReportVOS);
    }

    @ApiOperation(value = "渠道销售统计表")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping("/channel-sale-report")
    public ResponseEntity<List<ChannelSaleOrderVO>> queryChannelOrderSaleReport(@RequestBody SaleOrderOrderApiDTO saleOrderOrderApiDTO) {
        return Results.success(orderReportService.queryChannelOrderSaleReport(saleOrderOrderApiDTO));
    }

    @ApiOperation(value = "订单每日统计表")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping("/order-daily-report")
    public ResponseEntity<List<OrderSaleDateVO>> queryOrderSaleDateReport(@RequestBody OrderSaleDTO orderSaleDTO) {
        return Results.success(orderReportService.queryOrderSaleDateReport(orderSaleDTO));
    }

    @ApiOperation(value ="订单商品统计表")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping("/order-item-report")
    public ResponseEntity<List<SaleItemReportVO>> queryOrderItemReport(@RequestBody SaleItemReportDTO saleItemReportDTO) {
        return Results.success(orderReportService.queryOrderItemReport(saleItemReportDTO));
    }
}
