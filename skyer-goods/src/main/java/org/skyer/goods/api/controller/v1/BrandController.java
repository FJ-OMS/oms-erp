package org.skyer.goods.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.goods.api.dto.BrandPageReqDTO;
import org.skyer.goods.api.dto.BrandReqDTO;
import org.skyer.goods.api.dto.IdsReqDTO;
import org.skyer.goods.api.dto.StatusReqDTO;
import org.skyer.goods.config.GoodsSwaggerApiConfig;
import org.skyer.goods.domain.service.BrandService;
import org.skyer.goods.domain.vo.BrandPageResVO;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品品牌管理
 * @description
 * @Author wusc
 * @create 2021/6/24 9:41 上午
 */
@RestController("BrandController.v1")
@RequestMapping(value = "/v1/goods/brand")
@Api(tags = GoodsSwaggerApiConfig.GOODS_BRAND)
public class BrandController {

    @Autowired
    private BrandService brandService;


    @ApiOperation(value = "商品品牌查询")
    @PostMapping("/page")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<Page<BrandPageResVO>> pageBrand(@Valid @RequestBody BrandPageReqDTO brandPageReqDTO){
        return Results.success( brandService.pageBrand(brandPageReqDTO));
    }


    @ApiOperation(value = "商品品牌查询")
    @PostMapping("/list")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<List<BrandPageResVO>> listBrand(@Valid @RequestBody BrandPageReqDTO brandPageReqDTO){
        return Results.success( brandService.listBrand(brandPageReqDTO));
    }

    @ApiOperation(value = "创建商品品牌")
    @PostMapping("/brand")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<String> createBrand(@Valid @RequestBody BrandReqDTO brandReqDTO){
        brandService.saveOrUpdateBrand(brandReqDTO);
        return Results.success("操作成功");
    }

    @ApiOperation(value = "编辑商品品牌")
    @PutMapping("/brand")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<String> updateBrand(@Valid @RequestBody BrandReqDTO brandReqDTO){
        brandService.saveOrUpdateBrand(brandReqDTO);
        return Results.success("操作成功");
    }


    @ApiOperation(value = "删除商品品牌")
    @PatchMapping("/batch-delete-brand")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<String> deleteByIds(@RequestBody IdsReqDTO idsReqDTO) {
        brandService.deleteBrand(idsReqDTO.getIds());
        return Results.success("操作成功");
    }

    @ApiOperation(value = "更新状态")
    @PatchMapping("/status")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    public ResponseEntity<String> updateStatus(@Valid @RequestBody StatusReqDTO statusReqDTO){
        brandService.updateBrandStatus(statusReqDTO.getIds(),statusReqDTO.getStatus());
        return Results.success("操作成功");
    }

}
