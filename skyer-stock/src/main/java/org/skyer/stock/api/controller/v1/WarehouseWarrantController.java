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
import org.skyer.stock.api.dto.WarehouseWarrantPageQueryDTO;
import org.skyer.stock.api.dto.WarehouseWarrantSaveDTO;
import org.skyer.stock.config.StockSwaggerApiConfig;
import org.skyer.stock.domain.service.WarehouseWarrantService;
import org.skyer.stock.domain.vo.WarehouseWarrantInfoVO;
import org.skyer.stock.domain.vo.WarehouseWarrantVO;
import org.skyer.swagger.annotation.Permission;

/**
 * 入库单 管理 API
 *
 * @author lixiaoyang 2021-10-25 16:34:17
 */
@Api(tags = StockSwaggerApiConfig.WAREHOUSE_WARRANT_MANAGE)
@RestController("warehouseWarrantController.v1")
@RequestMapping("/v1/warehouse/warrants")
public class WarehouseWarrantController extends BaseController {

    @Autowired
    private WarehouseWarrantService warehouseWarrantService;

    @ApiOperation(value = "入库通知单列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/page")
    public ResponseEntity<Page<WarehouseWarrantVO>> page(@RequestBody WarehouseWarrantPageQueryDTO dto) {
        Page<WarehouseWarrantVO> page = warehouseWarrantService.page(dto);
        return Results.success(page);
    }


    @ApiOperation(value = "入库通知单详情")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/detail")
    public ResponseEntity<WarehouseWarrantInfoVO> detail(@RequestParam(value = "id", required = false) @Encrypt Long id,
                                                         @RequestParam(value = "code", required = false) String code) {
        WarehouseWarrantInfoVO vo = warehouseWarrantService.detail(id, code);
        return Results.success(vo);
    }

    @ApiOperation(value = "创建测试")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody WarehouseWarrantSaveDTO dto) {
        warehouseWarrantService.generateWarehouseWarrant(dto);
        return Results.success();
    }
}
