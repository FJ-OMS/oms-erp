package org.skyer.order.api.controller.v1;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import org.skyer.core.base.BaseController;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.order.api.dto.*;
import org.skyer.order.api.vo.*;
import org.skyer.order.app.service.AfterSaleHeaderService;
import org.skyer.order.domain.repository.AfterSaleHeaderRepository;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;

/**
 * 售后单 管理 API
 *
 * @author lzh 2022-03-16 10:55:39
 */
@RestController("afterSaleHeaderSiteController.v1")
@RequestMapping("/v1/after-sale-headers")
public class AfterSaleHeaderController extends BaseController {

    @Autowired
    private AfterSaleHeaderRepository afterSaleHeaderRepository;
    @Autowired
    private AfterSaleHeaderService afterSaleHeaderService;

    @ApiOperation(value = "增加额外的退款商品")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @PostMapping("/add-refund-only")
    public ResponseEntity<Void> addRefundOnlyItemLine(@RequestBody  AddRefundOnlyDTO addRefundOnlyDto) {
        afterSaleHeaderRepository.addRefundOnlyItemLine(addRefundOnlyDto);
        return Results.success();
    }

    @ApiOperation(value = "增加额外的退货商品")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @PostMapping("/add-refund-goods")
    public ResponseEntity<Void> addRefundGoodsItemLine(@RequestBody  AddRefundOnlyDTO addRefundOnlyDto) {
        afterSaleHeaderRepository.addRefundGoodsItemLine(addRefundOnlyDto);
        return Results.success();
    }

    @ApiOperation(value = "仅退款/退货退款删除商品行")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @DeleteMapping("/refund-delete-item")
    public ResponseEntity<Void> refundOnlyDeleteItem(@RequestBody @Valid AfterItemDeleteDTO afterItemDeleteDto) {
        afterSaleHeaderRepository.refundOnlyDeleteItem(afterItemDeleteDto);
        return Results.success();
    }


    @ApiOperation(value = "仅退款分页查询")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @PostMapping("/refund-only-page")
    public ResponseEntity<Page<RefundOnlyPageVO>> refundOnlyPage(@RequestBody RefundOnlyPageDTO refundOnlyPageDto) {
        Page<RefundOnlyPageVO> page = afterSaleHeaderRepository.refundOnlyPage(refundOnlyPageDto);
        return Results.success(page);
    }

    @ApiOperation(value = "创建仅退款单")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @PostMapping("/create-refund-only")
    public ResponseEntity<Void> createRefundOnly(@RequestBody  RefundGoodsDTO refundGoodsDTO) {
        afterSaleHeaderRepository.createOnlyRefund(refundGoodsDTO);
        return Results.success();
    }


    @ApiOperation(value = "创建退货退款单")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping("/create-refund-goods")
    public ResponseEntity<String> createRefundGoods(@RequestBody RefundGoodsDTO refundGoodsDTO) {
        afterSaleHeaderRepository.createRefundGoods(refundGoodsDTO);
        return Results.success("创建成功");
    }

    @ApiOperation(value = "退货退款单详情")
    @Permission(level = ResourceLevel.SITE  ,permissionLogin = true)
    @GetMapping("/refund-goods-detail/{orderId}")
    public ResponseEntity<RefundDetailsVO> queryBaseDetail(@PathVariable @Encrypt Long orderId){
        RefundDetailsVO refundDetailsVO =afterSaleHeaderRepository.queryBaseDetail(orderId,null);
        return Results.success(refundDetailsVO);
    }

    @ApiOperation(value = "退货退款单修改/打标")
    @Permission(level = ResourceLevel.SITE, permissionLogin = true)
    @PutMapping("/refund-goods-edit")
    public ResponseEntity<String> editBase(@RequestBody UpdateRefundDTO updateRefundDTO){
        afterSaleHeaderRepository.editBase(updateRefundDTO);
        return Results.success("修改成功");
    }

    @ApiOperation(value = "退货退款单列表")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @PostMapping("/refund-goods-page")
    public ResponseEntity<Page<RefundPageVO>> refundPage(@RequestBody RefundPageDTO refundPageDTO) {
        Page<RefundPageVO> vos =afterSaleHeaderRepository.refundPage(refundPageDTO);
        return Results.success(vos);
    }

    @ApiOperation(value = "退货退款单物流详情")
    @Permission(level = ResourceLevel.SITE  ,permissionLogin = true)
    @GetMapping("/refund-logistics/{orderId}")
    public ResponseEntity<List<RefundsLogisticsVO>> queryLogistics(@PathVariable @Encrypt Long orderId){
        List<RefundsLogisticsVO> vos = afterSaleHeaderRepository.refundsLogistics(orderId);
        return Results.success(vos);
    }

    @ApiOperation(value = "退货退款单审核列表")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @PostMapping("/refund-audit-page")
    public ResponseEntity<Page<RefundPageVO>> refundAuditPage(@RequestBody RefundAuditPageDTO refundPageDTO) {
        Page<RefundPageVO> vos =afterSaleHeaderRepository.refundAuditPage(refundPageDTO);
        return Results.success(vos);
    }

    @ApiOperation(value = "退货退款单详情(对账单详情调用)")
    @Permission(level = ResourceLevel.SITE  ,permissionLogin = true)
    @GetMapping("/detail/{refundCode}")
    public ResponseEntity<List<AfterSaleDetailVO>> getDetail(@PathVariable String refundCode){
        return Results.success(afterSaleHeaderRepository.getDetail(refundCode));
    }

    @ApiOperation(value = "全部入库")
    @Permission(level = ResourceLevel.SITE  ,permissionLogin = true)
    @PatchMapping("/confirm-ware/{orderId}")
    public ResponseEntity<String> confirmWarehousing(@PathVariable @Encrypt Long orderId){
        afterSaleHeaderRepository.confirmWarehousing(orderId);
        return Results.success("操作成功");
    }

    @ApiOperation(value = "内部售后单详情订单基本信息通过code和Id")
    @Permission(level = ResourceLevel.SITE  ,permissionLogin = true)
    @PostMapping("/order-base-detail-by-id-code")
    public ResponseEntity<RefundDetailsVO> queryOrderBaseDetailByDTO(@RequestBody OrderDetailSearchDTO orderDetailSearchDTO){
        RefundDetailsVO refundDetailsVO = afterSaleHeaderRepository.queryBaseDetail(orderDetailSearchDTO.getOrderId(),orderDetailSearchDTO.getOrderCode());
        return Results.success(refundDetailsVO);
    }

    @ApiOperation(value = "修改退货仓库")
    @Permission(level = ResourceLevel.SITE, permissionLogin = true)
    @PutMapping("/update-warehouse")
    public ResponseEntity<String> upadateWarehouse(@RequestBody UpdateWarehouseDTO updateWarehouseDTO){
        afterSaleHeaderRepository.updateWarehouse(updateWarehouseDTO);
        return Results.success("修改成功");
    }
}
