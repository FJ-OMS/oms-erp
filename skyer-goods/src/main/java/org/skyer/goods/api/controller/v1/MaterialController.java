package org.skyer.goods.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.goods.api.dto.IdsReqDTO;
import org.skyer.goods.api.dto.MaterialDTO;
import org.skyer.goods.api.dto.MaterialListDTO;
import org.skyer.goods.config.GoodsSwaggerApiConfig;
import org.skyer.goods.domain.service.MaterialService;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品素材
 * </p>
 *
 * @author chenzz
 * @since 2021-06-23
 */
@RestController("materialController.v1")
@RequestMapping("/v1/goods/material")
@Api(tags = GoodsSwaggerApiConfig.GOODS_MATERIAL)
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @ApiOperation(value = "分页获取信息")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/list")
    public ResponseEntity<?> pageList(MaterialListDTO materialListDTO, PageRequest pageRequest) {
        return Results.success(materialService.pageList(pageRequest, materialListDTO));
    }

    /**
     * 详细
     */
    @ApiOperation(value = "商品素材明细")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable("id") @Encrypt Long id) {
        return Results.success(materialService.getDetail(id));
    }

    /**
     * 详细2
     */
    @ApiOperation(value = "根据id获取商品素材明细")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/details")
    public ResponseEntity<?> showDetails(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id) {
        return Results.success(materialService.getDetail(id));
    }

    /**
     * 新增/添加
     */
    @ApiOperation("新增")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/add")
    public ResponseEntity<Void> add(@Validated @RequestBody MaterialDTO materialDTO) {
        return Boolean.TRUE.equals(materialService.add(materialDTO)) ? Results.success() : Results.error();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PutMapping("/edit")
    public ResponseEntity<Void> edit(@Validated @RequestBody MaterialDTO materialDTO) {
        if (materialDTO.getId() != null && materialDTO.getId() > 0) {
            return Boolean.TRUE.equals(materialService.edit(materialDTO)) ? Results.success() : Results.error();
        } else {
            // 修改必须设置id
            return Results.error();
        }
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteById(@ApiParam("主键ID") @RequestParam(value = "id") @Encrypt Long id) {
        return Boolean.TRUE.equals(materialService.deleteById(id)) ? Results.success() : Results.error();
    }

    @ApiOperation(value = "批量删除")
    @PatchMapping("/batch-delete")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<String> deleteByIds(@ApiParam("主键ID") @RequestBody IdsReqDTO idsReqDTO) {
        Boolean result = materialService.deleteByIds(idsReqDTO.getIds());
        return Boolean.TRUE.equals(result) ? Results.success("操作成功") : Results.error("删除出错!");
    }

}
