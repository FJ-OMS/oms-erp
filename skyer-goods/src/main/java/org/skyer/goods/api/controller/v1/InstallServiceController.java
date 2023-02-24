package org.skyer.goods.api.controller.v1;

import cn.hutool.core.lang.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.skyer.core.base.BaseController;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.goods.app.service.InstallServiceService;
import org.skyer.goods.config.GoodsSwaggerApiConfig;
import org.skyer.goods.domain.dto.InstallServiceDTO;
import org.skyer.goods.domain.dto.MultiDelDTO;
import org.skyer.goods.domain.dto.PageInstallServiceDTO;
import org.skyer.goods.domain.entity.InstallService;
import org.skyer.mybatis.pagehelper.annotation.SortDefault;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 安装服务 管理 API
 *
 * @author chenzz 2021-08-18 15:42:47
 */
@RestController("installServiceSiteController.v1")
@RequestMapping("/v1/install-services")
@Api(tags = GoodsSwaggerApiConfig.GOODS_INSTALL_SERVICE)
public class InstallServiceController extends BaseController {

    @Autowired
    private InstallServiceService installServiceService;

    @ApiOperation(value = "安装服务列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<?>> list(PageInstallServiceDTO installService, @ApiIgnore @SortDefault(value = InstallService.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<Dict> list = installServiceService.page(pageRequest, installService);
        return Results.success(list);
    }

    @ApiOperation(value = "安装服务明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@Encrypt @PathVariable Long id) {
        Dict detail = installServiceService.detail(id);
        return Results.success(detail);
    }

    @ApiOperation(value = "创建安装服务")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody InstallServiceDTO service) {
        validObject(service);
        boolean result = installServiceService.create(service);
        return result ? Results.success("添加成功") : Results.error("添加失败");
    }

    @ApiOperation(value = "修改安装服务")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping
    public ResponseEntity<?> update(@RequestBody InstallServiceDTO service) {
        // validObject(service);
        boolean result = installServiceService.update(service);
        return result ? Results.success("修改成功") : Results.error("修改失败");
    }

    @ApiOperation(value = "删除安装服务")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping
    public ResponseEntity<?> batchRemove(@ApiParam("主键ID") @RequestBody MultiDelDTO idsReqDTO) {
        Boolean result = installServiceService.batchRemove(idsReqDTO.getIds());
        return result ? Results.success("操作成功") : Results.error("删除出错!");
    }

}
