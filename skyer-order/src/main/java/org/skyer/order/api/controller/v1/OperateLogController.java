package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.api.dto.OperateLogDTO;
import org.skyer.order.domain.entity.OperateLog;
import org.skyer.order.domain.repository.OperateLogRepository;
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
 * 订单操作日志 管理 API
 *
 * @author wushaochuan 2022-01-18 14:34:53
 */
@RestController("operateLogSiteController.v1")
@RequestMapping("/v1/operate-logs")
public class OperateLogController extends BaseController {

    @Autowired
    private OperateLogRepository operateLogRepository;

    @ApiOperation(value = "订单操作日志列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<OperateLog>> list(OperateLog operateLog, @ApiIgnore @SortDefault(value = OperateLog.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<OperateLog> list = operateLogRepository.pageAndSort(pageRequest, operateLog);
        return Results.success(list);
    }

    @ApiOperation(value = "订单操作日志明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<OperateLog> detail(@PathVariable @Encrypt Long id) {
        OperateLog operateLog = operateLogRepository.selectByPrimaryKey(id);
        return Results.success(operateLog);
    }

    @ApiOperation(value = "创建订单操作日志")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody OperateLog operateLog) {
        operateLogRepository.insertSelective(operateLog);
        return Results.success();
    }

    @ApiOperation(value = "修改订单操作日志")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<OperateLog> update(@RequestBody OperateLog operateLog) {
        operateLogRepository.updateByPrimaryKeySelective(operateLog);
        return Results.success(operateLog);
    }

    @ApiOperation(value = "删除订单操作日志")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody OperateLog operateLog) {
        operateLogRepository.deleteByPrimaryKey(operateLog);
        return Results.success();
    }

}
