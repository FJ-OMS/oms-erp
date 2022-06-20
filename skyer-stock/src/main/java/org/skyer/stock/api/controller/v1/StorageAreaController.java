package org.skyer.stock.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.skyer.core.base.BaseController;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.stock.api.dto.BatchRemoveDTO;
import org.skyer.stock.api.dto.StorageAreaDTO;
import org.skyer.stock.api.dto.StorageAreaListReqDTO;
import org.skyer.stock.config.StockSwaggerApiConfig;
import org.skyer.stock.domain.entity.StorageArea;
import org.skyer.stock.domain.repository.StorageAreaRepository;
import org.skyer.stock.domain.service.StorageAreaService;
import org.skyer.stock.domain.vo.StorageAreaListVO;
import org.skyer.swagger.annotation.Permission;

/**
 * 仓储区域 管理 API
 *
 * @author linzhuanghuang@ebc.com 2021-08-13 17:53:17
 */
@Api(tags = StockSwaggerApiConfig.STORAGE_AREA_MANAGE)
@RestController("storageAreaController.v1")
@RequestMapping("/v1/storage/area")
public class StorageAreaController extends BaseController {

    @Autowired
    private StorageAreaRepository storageAreaRepository;

    @Autowired
    private StorageAreaService storageAreaService;

    @ApiOperation(value = "仓储区域表列表")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @GetMapping
    public ResponseEntity<Page<StorageAreaListVO>> page(StorageAreaListReqDTO listReqDTO, PageRequest pageRequest) {
        Page<StorageAreaListVO> page = storageAreaRepository.pageStorageAreaList(pageRequest, listReqDTO);
        return Results.success(page);
    }

    @ApiOperation(value = "仓储区域明细")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @GetMapping("/{id}")
    public ResponseEntity<StorageAreaDTO> detail(@PathVariable @Encrypt Long id) {
        StorageAreaDTO storageAreaDTO = storageAreaService.getDetails(id);
        return Results.success(storageAreaDTO);
    }

    @ApiOperation(value = "创建仓储区域")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody StorageAreaDTO storageAreaDTO) {
        storageAreaService.createStorageArea(storageAreaDTO);
        return Results.success();
    }

    @ApiOperation(value = "修改仓储区域")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @PutMapping
    public ResponseEntity<StorageArea> update(@RequestBody StorageAreaDTO storageAreaDTO) {
        storageAreaService.updateStorageArea(storageAreaDTO);
        return Results.success();
    }

    @ApiOperation("禁用 OR 启用")
    @PostMapping("/operation")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<Void> operation(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id,
                                          @ApiParam("true OR false") @RequestParam(value = "flag") Boolean enableFlag) {
        return storageAreaService.operation(id, enableFlag) ? Results.success() : Results.error();
    }


    @ApiOperation("批量删除数据")
    @PatchMapping("/batchDelete")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Void> batchDelete(@ApiParam("批量删除集合") @RequestBody @Encrypt BatchRemoveDTO batchRemoveDTO) {
        storageAreaService.batchDelete(batchRemoveDTO);
        return Results.success();
    }
}
