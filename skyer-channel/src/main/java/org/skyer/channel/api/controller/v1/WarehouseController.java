package org.skyer.channel.api.controller.v1;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

import org.skyer.channel.api.dto.ShopDeleteDTO;
import org.skyer.channel.api.dto.WarehouseDTO;
import org.skyer.channel.api.dto.WarehouseListDTO;
import org.skyer.channel.config.ChannelSwaggerApiConfig;
import org.skyer.channel.domain.entity.Warehouse;
import org.skyer.channel.domain.service.WarehouseService;
import org.skyer.channel.domain.vo.WarehouseListVO;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.mybatis.pagehelper.annotation.SortDefault;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;






/**
 * <p>
 *  渠道仓库管理
 * </p>
 *
 * @author lzh
 * @since 2021-06-18
 */
@RestController("WarehouseController.v1")
@RequestMapping(value = "/v1/channel/warehouse")
@Api(tags = ChannelSwaggerApiConfig.WAREHOUSE_MANAGE)
public class WarehouseController {


    @Autowired
    private WarehouseService warehouseService;

    @ApiOperation(value = "分页获取渠道仓库信息")
    @GetMapping("/list")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<Page<WarehouseListVO>> pageList( WarehouseListDTO warehouseListDTO, PageRequest pageRequest) {
    return Results.success( warehouseService.pageWarehouseList(pageRequest,warehouseListDTO));
    }

    @ApiOperation(value = "修改渠道仓库")
    @PostMapping("/update")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<Void> updatWarehouse(@RequestBody WarehouseDTO warehouse) {
        return warehouseService.updateWarehouse (warehouse)? Results.success() : Results.error();
    }

    @ApiOperation(value = "新增渠道仓库")
    @PostMapping("/add")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<Warehouse> createLabel(@RequestBody Warehouse  warehouse) {
        return warehouseService.createWarehouse (warehouse) ? Results.success() : Results.error() ;
    }

    @ApiOperation(value = "删除渠道仓库")
    @PostMapping("/delete")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<Void> deleteById(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id) {
        return warehouseService.deleteById(id) ? Results.success() : Results.error() ;

    }


    @ApiOperation(value = "根据Id获取渠道仓库信息")
    @GetMapping("/details")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<Warehouse> getWarehouseDetail(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id) {
        return Results.success(warehouseService.getWarehouseDetail(id));
    }

    @ApiOperation("禁用 OR 启用")
    @PostMapping("/operation")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<Void> operation(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id,
                                          @ApiParam("true OR false") @RequestParam(value = "flag") Boolean flag) {
        return warehouseService.operation(id, flag) ? Results.success() : Results.error();
    }
    @ApiOperation("批量删除数据")
    @PostMapping("/deletes")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Void> deletes(@ApiParam("批量删除集合") @RequestBody @Encrypt ShopDeleteDTO shopDeleteDTO) {
        return warehouseService.deletes(shopDeleteDTO) ? Results.success() : Results.error();
    }
    }
