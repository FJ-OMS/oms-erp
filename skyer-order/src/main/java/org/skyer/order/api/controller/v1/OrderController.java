package org.skyer.order.api.controller.v1;

import cn.hutool.core.collection.CollectionUtil;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.skyer.core.base.BaseController;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.order.api.dto.*;
import org.skyer.order.api.es.SearchOrderAuditEsParam;
import org.skyer.order.api.es.SearchOrderEsParam;
import org.skyer.order.api.es.SearchStockoutEsParam;
import org.skyer.order.api.vo.*;
import org.skyer.order.app.dto.OrderLineWorkDTO;
import org.skyer.order.app.service.*;
import org.skyer.order.app.service.saga.OrderForwardSagaService;
import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.repository.HeaderRepository;
import org.skyer.order.domain.repository.ItemLineRepository;
import org.skyer.order.domain.repository.ItemRepository;
import org.skyer.order.infra.common.CommonConstants;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description
 * @Author wusc
 * @create 2022/1/18 2:37 下午
 */
@RestController("orderController.v1")
@RequestMapping("/v1/order")
public class OrderController extends BaseController {
    @Autowired
    private HeaderService headerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderEsService orderEsService;
    @Autowired
    private ItemLineService itemLineService;
    @Autowired
    private ItemLineRepository itemLineRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StockoutEsService stockoutEsService;
    @Autowired
    private HeaderRepository headerRepository;

    @ApiOperation(value = "创建手工单")
    @Permission(level = ResourceLevel.SITE  ,permissionLogin = true)
    @PostMapping("/hand-order")
    public ResponseEntity<String> createHandOrder(@RequestBody @Valid OrderTableDTO orderTableDTO){
        orderService.createInnerOrderBpmn(orderTableDTO);
        return Results.success("订单创建成功");
    }


    @ApiOperation(value = "订单列表")
    @Permission(level = ResourceLevel.SITE  ,permissionLogin = true)
    @PostMapping("/order-page")
    public ResponseEntity<Page<OrderPageVo>> orderPage(@RequestBody SearchOrderEsParam searchOrderEsParam) throws Exception {
        Page<OrderPageVo> list = orderEsService.queryOrderPageEs(searchOrderEsParam);
        return Results.success(list);
    }

    @ApiOperation(value = "订单详情订单基本信息")
    @Permission(level = ResourceLevel.SITE  ,permissionLogin = true)
    @GetMapping("/order-base-detail/{orderId}")
    public ResponseEntity<OrderTotalVO> queryOrderBaseDetail(@PathVariable @Encrypt Long orderId){
        OrderTotalVO orderTotalVO = headerService.queryOrderTotal(orderId,null);
        return Results.success(orderTotalVO);
    }

    @ApiOperation(value = "订单详情订单基本信息通过code和Id")
    @Permission(level = ResourceLevel.SITE  ,permissionLogin = true)
    @PostMapping("/order-base-detail-by-id-code")
    public ResponseEntity<OrderTotalVO> queryOrderBaseDetailByDTO(@RequestBody OrderDetailSearchDTO orderDetailSearchDTO){
        OrderTotalVO orderTotalVO = headerService.queryOrderTotal(orderDetailSearchDTO.getOrderId(),orderDetailSearchDTO.getOrderCode());
        return Results.success(orderTotalVO);
    }

    @ApiOperation(value = "订单基础信息的修改")
    @Permission(level = ResourceLevel.SITE, permissionLogin = true)
    @PutMapping("/order-base")
    public ResponseEntity<String> editOrderBase(@RequestBody OrderBaseDTO orderBaseDTO){
        headerService.editOrderBase(orderBaseDTO);
        return Results.success("订单基础信息修改成功");
    }


    @ApiOperation(value = "获取订单平摊行列表")
    @Permission(level = ResourceLevel.SITE  ,permissionLogin = true)
    @PostMapping("/item-line-page")
    public ResponseEntity<Page<OrderItemLinePageVO>> queryItemLinePage(@RequestBody  @Valid ItemSearchDTO itemSearchDTO){
        return Results.success(itemLineService.queryItemLinePage(itemSearchDTO));
    }

    @ApiOperation(value = "根据itemId获取平摊商品行列表")
    @Permission(level = ResourceLevel.SITE  ,permissionLogin = true)
    @GetMapping("/item-line-details/{itemId}")
    public ResponseEntity<List<OrderItemLinePageVO>> queryItemLineList(@PathVariable @Encrypt Long itemId){
        return Results.success(itemLineRepository.queryItemLineList(itemId));
    }

    @ApiOperation(value = "获取订单Item列表")
    @Permission(level = ResourceLevel.SITE ,permissionLogin = true)
    @PostMapping("/order-item-page")
    public ResponseEntity<Page<OrderItemPageVO>> queryItemPage(@RequestBody  @Valid ItemSearchDTO itemSearchDTO){
        return Results.success(itemRepository.queryItemPage(itemSearchDTO));
    }

    @ApiOperation(value = "订单批量插入ES")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @PostMapping("/batch-insert-order")
    public ResponseEntity<String> batchInsertOrderToEs(@RequestBody List<String> innerOrderNoList){
        if (CollectionUtil.isNotEmpty(innerOrderNoList)){
            orderEsService.batchInsertOrderToEs(innerOrderNoList);
        }else {
            List<Header> headers = headerRepository.selectAll();
            innerOrderNoList = headers.stream().map(Header::getInnerOrderNo).collect(Collectors.toList());
            orderEsService.batchInsertOrderToEs(innerOrderNoList);
        }
        return Results.success("操作成功");
    }


    @ApiOperation(value = "复制订单")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @GetMapping("/copy-order")
    public ResponseEntity<CopyOrderVO> copyOrder(@RequestParam("innerOrderNo") String innerOrderNo) {
        CopyOrderVO copyOrderVO = headerService.copyOrder(innerOrderNo);
        return Results.success(copyOrderVO);
    }

    @ApiOperation(value = "订单级-确认收货")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @PatchMapping("/confirm-receipt/{orderId}")
    public ResponseEntity<String> confirmReceipt(@PathVariable @Encrypt Long orderId){
        headerService.confirmReceipt(orderId);
        return Results.success("操作成功");
    }

    @ApiOperation(value = "商品行级-确认收货")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @PostMapping("/item-confirm-receipt")
    public ResponseEntity<String> itemConfirmReceipt(@RequestBody  ItemConfirmDTO itemConfirmDTO){
        headerService.itemConfirmReceipt(itemConfirmDTO);
        return Results.success("操作成功");
    }

    @ApiOperation(value= "订单取消")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @PatchMapping("/cancel-order")
    public ResponseEntity<String> cancelOrder(@RequestBody OrderCancelDTO orderCancelDTO){
        orderService.cancelOrder(orderCancelDTO);
        return Results.success("订单取消成功");
    }


}
