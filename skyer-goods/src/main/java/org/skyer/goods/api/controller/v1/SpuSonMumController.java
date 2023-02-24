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
import org.skyer.goods.app.service.SpuSonMumService;
import org.skyer.goods.config.GoodsSwaggerApiConfig;
import org.skyer.goods.domain.dto.PageSonMumDTO;
import org.skyer.goods.domain.dto.SpuSonMumDTO;
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
 * 子母商品 管理 API
 *
 * @author chenzz 2021-08-16 15:04:38
 */
@RestController("spuSonMumController.v1")
@RequestMapping("/v1/spu-son-mums")
@Api(tags = GoodsSwaggerApiConfig.GOODS_SPU_SON_MUM)
public class SpuSonMumController extends BaseController {

    @Autowired
    private SpuSonMumService spuSonMumService;

    @ApiOperation(value = "子母商品列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    public ResponseEntity<Page<?>> list(PageSonMumDTO spuSonMum, @ApiIgnore @SortDefault(value = SpuSonMum.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<Dict> list = spuSonMumService.page(pageRequest, spuSonMum);
        return Results.success(list);
    }

    @ApiOperation(value = "子母商品明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@Encrypt @PathVariable Long id) {
        Dict spuSonMum = spuSonMumService.detail(id);
        return Results.success(spuSonMum);
    }

    @ApiOperation(value = "创建子母商品")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody SpuSonMumDTO spuSonMumDTO) {
        validObject(spuSonMumDTO);
        boolean result = spuSonMumService.create(spuSonMumDTO);
        return result ? Results.success("添加成功") : Results.error("添加失败");
    }

    @ApiOperation(value = "修改子母商品")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping
    public ResponseEntity<?> update(@RequestBody SpuSonMumDTO spuSonMumDTO) {
        validObject(spuSonMumDTO);
        boolean result = spuSonMumService.update(spuSonMumDTO);
        return result ? Results.success("修改成功") : Results.error("修改失败");
    }

    @ApiOperation(value = "删除子母商品")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping
    public ResponseEntity<?> batchRemove(@ApiParam("主键ID") @RequestBody IdsReqDTO idsReqDTO) {
        Boolean result = spuSonMumService.batchRemove(idsReqDTO.getIds());
        return result ? Results.success("操作成功") : Results.error("删除出错!");
    }

}
