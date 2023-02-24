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
import org.skyer.stock.api.dto.ResultRecordPageDTO;
import org.skyer.stock.config.StockSwaggerApiConfig;
import org.skyer.stock.domain.service.ResultRecordService;
import org.skyer.stock.domain.vo.ResultRecordInfoVO;
import org.skyer.stock.domain.vo.ResultRecordListVO;
import org.skyer.swagger.annotation.Permission;

/**
 * 收发货结果单 管理 API
 *
 * @author lixiaoyang 2021-08-03 16:41:29
 */
@Api(tags = StockSwaggerApiConfig.RESULT_RECORD)
@RestController("resultRecordController.v1")
@RequestMapping("/v1/result-records")
public class ResultRecordController extends BaseController {

    @Autowired
    private ResultRecordService resultRecordService;

    @ApiOperation("出库结果单分页")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/page")
    public ResponseEntity<Page<ResultRecordListVO>> page(@RequestBody ResultRecordPageDTO dto){
        Page<ResultRecordListVO> page = resultRecordService.page(dto);

        return Results.success(page);
    }

    @ApiOperation("出库结果单详情")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/{id}")
    public ResponseEntity<ResultRecordInfoVO> detail(@PathVariable @Encrypt Long id){
        ResultRecordInfoVO detail = resultRecordService.detail(id);

        return Results.success(detail);
    }
}
