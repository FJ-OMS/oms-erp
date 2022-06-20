package org.skyer.stock.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.skyer.core.base.BaseController;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.stock.api.dto.AdjustPageReqDTO;
import org.skyer.stock.api.dto.AdjustRecordReqDTO;
import org.skyer.stock.api.dto.AuditReqDTO;
import org.skyer.stock.config.StockSwaggerApiConfig;
import org.skyer.stock.domain.service.AdjustRecordService;
import org.skyer.stock.domain.vo.AdjustRecordPageResVO;
import org.skyer.stock.domain.vo.AdjustRecordResVO;
import org.skyer.swagger.annotation.Permission;

/**
 * 库存调整单 管理 API
 *
 * @author lixiaoyang 2021-08-02 16:44:33
 */
@Api(tags = StockSwaggerApiConfig.ADJUST_RECORD)
@RestController("adjustRecordController.v1")
@RequestMapping("/v1/adjust-records")
public class AdjustRecordController extends BaseController {
    @Autowired
    private AdjustRecordService adjustRecordService;

    @ApiOperation(value = "创建或者更新调整单")
    @PutMapping("/adjust-record")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<String> createOrUpdateAdjustRecord(@RequestBody AdjustRecordReqDTO adjustRecordReqDTO) {
        adjustRecordService.createOrUpdateAdjustRecord(adjustRecordReqDTO);
        return Results.success("操作成功");
    }

    @ApiOperation(value = "调整单分页")
    @PostMapping("/page")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Page<AdjustRecordPageResVO>> pageAdjustRecord(@RequestBody AdjustPageReqDTO adjustPageReqDTO) {
        Page<AdjustRecordPageResVO> adjustRecordPageResVOS = adjustRecordService.pageAdjustRecord(adjustPageReqDTO);
        return Results.success(adjustRecordPageResVOS);
    }

    @ApiOperation(value = "审核调整单")
    @PostMapping("/adjust-status")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<String> auditAdjustRecord(@RequestBody AuditReqDTO auditReqDTO) {
       adjustRecordService.auditAdjustRecord(auditReqDTO.getId(),auditReqDTO.getStatus());
        return Results.success("操作成功");
    }

    @ApiOperation(value = "查看调整单详情")
    @GetMapping("/detail/{adjustId}")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<AdjustRecordResVO> adjustDetail(@PathVariable("adjustId") @Encrypt Long adjustId) {
        AdjustRecordResVO adjustRecordResVO = adjustRecordService.adjustDetail(adjustId);
        return Results.success(adjustRecordResVO);
    }
}
