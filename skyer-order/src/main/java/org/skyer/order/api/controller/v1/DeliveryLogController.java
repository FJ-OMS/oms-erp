package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.domain.entity.DeliveryLog;
import org.skyer.order.domain.repository.DeliveryLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.skyer.mybatis.helper.SecurityTokenHelper;

import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.mybatis.pagehelper.annotation.SortDefault;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.skyer.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 订单发货日志 管理 API
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@RestController("deliveryLogSiteController.v1")
@RequestMapping("/v1/delivery-logs")
public class DeliveryLogController extends BaseController {

    @Autowired
    private DeliveryLogRepository deliveryLogRepository;

    @ApiOperation(value = "订单发货日志列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<DeliveryLog>> list(DeliveryLog deliveryLog, @ApiIgnore @SortDefault(value = DeliveryLog.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<DeliveryLog> list = deliveryLogRepository.pageAndSort(pageRequest, deliveryLog);
        return Results.success(list);
    }

    @ApiOperation(value = "订单发货日志明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryLog> detail(@PathVariable Long id) {
        DeliveryLog deliveryLog = deliveryLogRepository.selectByPrimaryKey(id);
        return Results.success(deliveryLog);
    }

    @ApiOperation(value = "创建订单发货日志")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<DeliveryLog> create(@RequestBody DeliveryLog deliveryLog) {
        validObject(deliveryLog);
        deliveryLogRepository.insertSelective(deliveryLog);
        return Results.success(deliveryLog);
    }

    @ApiOperation(value = "修改订单发货日志")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<DeliveryLog> update(@RequestBody DeliveryLog deliveryLog) {
        SecurityTokenHelper.validToken(deliveryLog);
        deliveryLogRepository.updateByPrimaryKeySelective(deliveryLog);
        return Results.success(deliveryLog);
    }

    @ApiOperation(value = "删除订单发货日志")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody DeliveryLog deliveryLog) {
        SecurityTokenHelper.validToken(deliveryLog);
        deliveryLogRepository.deleteByPrimaryKey(deliveryLog);
        return Results.success();
    }

}
