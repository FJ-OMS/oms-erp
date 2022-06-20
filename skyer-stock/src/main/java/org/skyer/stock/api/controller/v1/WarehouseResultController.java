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
import org.skyer.stock.api.dto.WarehouseResultPageQueryDTO;
import org.skyer.stock.config.StockSwaggerApiConfig;
import org.skyer.stock.domain.service.WarehouseResultService;
import org.skyer.stock.domain.vo.WarehouseResultInfoVO;
import org.skyer.stock.domain.vo.WarehouseResultVO;
import org.skyer.swagger.annotation.Permission;

/**
 * 入库结果单 管理 API
 *
 * @author xurongfa 2022-04-26 14:10:52
 */
@Api(tags = StockSwaggerApiConfig.WAREHOUSE_RESULT_MANAGE)
@RestController("warehouseResultController.v1")
@RequestMapping("/v1/warehouse-results")
public class WarehouseResultController extends BaseController {

    @Autowired
    private WarehouseResultService warehouseResultService;

    @ApiOperation(value = "入库结果单列表")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/page")
    public ResponseEntity<Page<WarehouseResultVO>> page(@RequestBody WarehouseResultPageQueryDTO dto) {
        Page<WarehouseResultVO> page = warehouseResultService.page(dto);
        return Results.success(page);
    }

    @ApiOperation(value = "入库结果单详情")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/detail")
    public ResponseEntity<WarehouseResultInfoVO> detail(@RequestParam(value = "id",required = false) @Encrypt Long id,
                                                      @RequestParam(value = "code",required = false) String code) {
        WarehouseResultInfoVO vo = warehouseResultService.detail(id, code);
        return Results.success(vo);
    }
}
