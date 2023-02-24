package org.skyer.channel.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import org.skyer.channel.api.dto.PhysicalShopDTO;
import org.skyer.channel.api.dto.PhysicalShopShowDTO;
import org.skyer.channel.api.dto.ShopDeleteDTO;
import org.skyer.channel.config.ChannelSwaggerApiConfig;
import org.skyer.channel.domain.service.PhysicalShopService;
import org.skyer.channel.domain.vo.PhysicalShopDetailVO;
import org.skyer.channel.domain.vo.PhysicalShopVO;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;

/**
 * 门店管理
 *
 * @author lixiaoyang
 * @date 2021/6/16 15:19
 */
@Api(tags = ChannelSwaggerApiConfig.PHYSICAL_SHOP_MANAGE)
@RestController("physicalShopController.v1")
@RequestMapping("/v1/physical/shop")
public class PhysicalShopController {
    @Autowired
    private PhysicalShopService physicalShopService;

    @ApiOperation("查询列表")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/page")
    public ResponseEntity<Page<PhysicalShopVO>> showPage(PhysicalShopShowDTO physicalShopShowDTO, PageRequest pageRequest) {
        return Results.success(physicalShopService.getPage(physicalShopShowDTO, pageRequest));
    }

    @ApiOperation("查询详情")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/details")
    public ResponseEntity<PhysicalShopDetailVO> showDetails(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id) {
        return Results.success(physicalShopService.getDetails(id));
    }

    @ApiOperation("新增与修改")
    @PostMapping("/save")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Void> save(@Valid @RequestBody @Encrypt PhysicalShopDTO physicalShop) {
        return physicalShopService.save(physicalShop) ? Results.success() : Results.error();
    }

    @ApiOperation("删除数据")
    @PostMapping("/delete")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Void> delete(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id,
                                       @ApiParam("当前版本号") @RequestParam(value = "objectVersionNumber") Long objectVersionNumber) {
        return physicalShopService.delete(id, objectVersionNumber) ? Results.success() : Results.error();
    }

    @ApiOperation("禁用 OR 启用")
    @PostMapping("/operation")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Void> operation(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id,
                                          @ApiParam("true OR false") @RequestParam(value = "flag") Boolean flag,
                                          @ApiParam("当前版本号") @RequestParam(value = "objectVersionNumber") Long objectVersionNumber) {
        return physicalShopService.operation(id, flag, objectVersionNumber) ? Results.success() : Results.error();
    }

    @ApiOperation("清除缓存")
    @PostMapping("/delete/cache")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Void> deleteCache(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id) {
        physicalShopService.deleteCache(id);
        return Results.success();
    }

    @ApiOperation("批量删除数据")
    @PostMapping("/deletes")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Void> deletes(@ApiParam("批量删除集合") @RequestBody @Encrypt ShopDeleteDTO shopDeleteDTO) {
        return physicalShopService.deletes(shopDeleteDTO) ? Results.success() : Results.error();
    }

}
