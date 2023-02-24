package org.skyer.stock.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.skyer.core.base.BaseController;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.stock.api.dto.WarehouseQueryReqDTO;
import org.skyer.stock.config.StockSwaggerApiConfig;
import org.skyer.stock.domain.service.WarehouseService;
import org.skyer.stock.domain.vo.WarehouseQueryResVO;
import org.skyer.swagger.annotation.Permission;

/**
 * 仓库表 管理 API
 *
 * @author lixiaoyang 2021-07-28 15:31:35
 */
@Api(tags = StockSwaggerApiConfig.WAREHOUSE_MANAGE)
@RestController("warehouseController.v1")
@RequestMapping("/v1/warehouses")
public class WarehouseController extends BaseController {
    @Autowired
    private WarehouseService warehouseService;

    @ApiOperation(value = "仓库查询分页")
    @PostMapping("/page-warehouse")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Page<WarehouseQueryResVO>> queryWarehousePage(@RequestBody WarehouseQueryReqDTO warehouseQueryReqDTO) {
        return Results.success(warehouseService.queryWarehousePage(warehouseQueryReqDTO));
    }
}
