package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.domain.entity.ServiceLog;
import org.skyer.order.domain.repository.ServiceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.skyer.mybatis.helper.SecurityTokenHelper;

import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.mybatis.pagehelper.annotation.SortDefault;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 订单中心与其他中心交互日志 管理 API
 *
 * @author linzhuanghuang@ebc.com 2022-02-28 13:24:39
 */
@RestController("serviceLogController.v1")
@RequestMapping("/v1/service-logs")
public class ServiceLogController extends BaseController {

    @Autowired
    private ServiceLogRepository serviceLogRepository;

    @ApiOperation(value = "订单中心与其他中心交互日志列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<ServiceLog>> list(ServiceLog serviceLog, @ApiIgnore @SortDefault(value = ServiceLog.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<ServiceLog> list = serviceLogRepository.pageAndSort(pageRequest, serviceLog);
        return Results.success(list);
    }

    @ApiOperation(value = "订单中心与其他中心交互日志明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<ServiceLog> detail(@PathVariable @Encrypt Long id) {
        ServiceLog serviceLog = serviceLogRepository.selectByPrimaryKey(id);
        return Results.success(serviceLog);
    }

    @ApiOperation(value = "创建订单中心与其他中心交互日志")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<ServiceLog> create(@RequestBody ServiceLog serviceLog) {
        serviceLogRepository.insertSelective(serviceLog);
        return Results.success(serviceLog);
    }

    @ApiOperation(value = "修改订单中心与其他中心交互日志")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<ServiceLog> update(@RequestBody ServiceLog serviceLog) {
        serviceLogRepository.updateByPrimaryKeySelective(serviceLog);
        return Results.success(serviceLog);
    }

    @ApiOperation(value = "删除订单中心与其他中心交互日志")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody ServiceLog serviceLog) {
        serviceLogRepository.deleteByPrimaryKey(serviceLog);
        return Results.success();
    }

}
