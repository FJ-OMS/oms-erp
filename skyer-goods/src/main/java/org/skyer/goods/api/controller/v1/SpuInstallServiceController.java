package org.skyer.goods.api.controller.v1;

import cn.hutool.core.lang.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.skyer.core.base.BaseController;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.goods.api.dto.IdsReqDTO;
import org.skyer.goods.app.service.SpuInstallServiceService;
import org.skyer.goods.config.GoodsSwaggerApiConfig;
import org.skyer.goods.domain.dto.PageSpuInstallServiceDTO;
import org.skyer.goods.domain.dto.SpuInstallServiceDTO;
import org.skyer.goods.domain.entity.SpuInstallService;
import org.skyer.goods.domain.entity.SpuSonMum;
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
 * 商品安装服务 管理 API
 *
 * @author chenzz 2021-08-18 16:42:11
 */
@RestController("spuInstallServiceSiteController.v1")
@RequestMapping("/v1/spu-install-services")
@Api(tags = GoodsSwaggerApiConfig.GOODS_SPU_INSTALL_SERVICE)
public class SpuInstallServiceController extends BaseController {

    @Autowired
    private SpuInstallServiceService spuInstallServiceService;

    @ApiOperation(value = "商品安装服务列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    public ResponseEntity<Page<?>> list(PageSpuInstallServiceDTO serviceDTO, @ApiIgnore @SortDefault(value = SpuInstallService.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<Dict> list = spuInstallServiceService.page(pageRequest, serviceDTO);
        return Results.success(list);
    }

    @ApiOperation(value = "商品安装服务明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@Encrypt @PathVariable Long id) {
        Dict installService = spuInstallServiceService.detail(id);
        return Results.success(installService);
    }

    @ApiOperation(value = "创建商品安装服务")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<SpuSonMum> create(@RequestBody SpuInstallServiceDTO dto) {
        validObject(dto);
        boolean result = spuInstallServiceService.create(dto);
        return result ? Results.success() : Results.error();
    }

    @ApiOperation(value = "修改商品安装服务")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping
    public ResponseEntity<SpuSonMum> update(@RequestBody SpuInstallServiceDTO dto) {
        validObject(dto);
        boolean result = spuInstallServiceService.update(dto);
        return result ? Results.success() : Results.error();
    }

    @ApiOperation(value = "删除商品安装服务")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping
    public ResponseEntity<?> batchRemove(@ApiParam("主键ID") @RequestBody IdsReqDTO idsReqDTO) {
        Boolean result = spuInstallServiceService.batchRemove(idsReqDTO.getIds());
        return result ? Results.success("操作成功") : Results.error("删除出错!");
    }

}
