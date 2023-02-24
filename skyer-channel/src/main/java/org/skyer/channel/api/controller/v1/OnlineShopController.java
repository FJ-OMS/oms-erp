package org.skyer.channel.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import org.skyer.channel.api.dto.*;
import org.skyer.channel.config.ChannelSwaggerApiConfig;
import org.skyer.channel.domain.service.OnlineShopService;
import org.skyer.channel.domain.vo.OnlineShopDetailVO;
import org.skyer.channel.domain.vo.OnlineShopSettingVO;
import org.skyer.channel.domain.vo.OnlineShopVO;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;

/**
 * 网店管理
 *
 * @author lixiaoyang
 * @date 2021/6/16 15:19
 */
@Api(tags = ChannelSwaggerApiConfig.ONLINE_SHOP_MANAGE)
@RestController("onlineShopController.v1")
@RequestMapping("/v1/online/shop")
public class OnlineShopController {
    @Autowired
    private OnlineShopService onlineShopService;

    @ApiOperation("查询列表")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/page")
    public ResponseEntity<Page<OnlineShopVO>> showPage(OnlineShopShowDTO onlineShopShowDTO, PageRequest pageRequest) {
        return Results.success(onlineShopService.getPage(onlineShopShowDTO, pageRequest));
    }

    @ApiOperation("查询详情")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/details")
    public ResponseEntity<OnlineShopDetailVO> showDetails(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id) {
        return Results.success(onlineShopService.getDetails(id));
    }

    @ApiOperation("查询详情")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/details/by/code")
    public ResponseEntity<OnlineShopDetailVO> showDetailsByCode(@ApiParam("编码") @RequestParam(value = "code") String code) {
        return Results.success(onlineShopService.getDetailsByCode(code));
    }


    @ApiOperation("新增与修改")
    @PostMapping("/save")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Void> save(@Valid @RequestBody @Encrypt OnlineShopDTO onlineShopDTO) {
        return onlineShopService.save(onlineShopDTO) ? Results.success() : Results.error();
    }

    @ApiOperation("网店设置")
    @PostMapping("/setting")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Void> setting(@Valid @RequestBody @Encrypt OnlineShopSettingDTO onlineShopSettingDTO) {
        return onlineShopService.setting(onlineShopSettingDTO) ? Results.success() : Results.error();
    }

    @ApiOperation("删除数据")
    @PostMapping("/delete")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Void> delete(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id,
                                       @ApiParam("当前版本号") @RequestParam(value = "objectVersionNumber") Long objectVersionNumber) {
        return onlineShopService.delete(id, objectVersionNumber) ? Results.success() : Results.error();
    }

    @ApiOperation("禁用 OR 启用")
    @PostMapping("/operation")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Void> operation(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id,
                                          @ApiParam("true OR false") @RequestParam(value = "flag") Boolean flag,
                                          @ApiParam("当前版本号") @RequestParam(value = "objectVersionNumber") Long objectVersionNumber) {
        return onlineShopService.operation(id, flag, objectVersionNumber) ? Results.success() : Results.error();
    }

    @ApiOperation("清除缓存")
    @PostMapping("/delete/cache")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Void> deleteCache(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id) {
        onlineShopService.deleteCache(id);
        return Results.success();
    }

    @ApiOperation("查询设置")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/get/setting")
    public ResponseEntity<OnlineShopSettingVO> getSetting(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id) {
        return Results.success(onlineShopService.getSetting(id));
    }

    @ApiOperation("批量删除数据")
    @PostMapping("/deletes")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<Void> deletes(@ApiParam("批量删除集合") @RequestBody @Encrypt ShopDeleteDTO shopDeleteDTO) {
        return onlineShopService.deletes(shopDeleteDTO) ? Results.success() : Results.error();
    }

}
