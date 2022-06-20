package org.skyer.goods.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import org.skyer.core.base.BaseController;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.goods.api.dto.CategoryQueryDTO;
import org.skyer.goods.api.dto.CategorySaveDTO;
import org.skyer.goods.api.dto.CategoryTreeQueryDTO;
import org.skyer.goods.api.dto.CategoryUpdateByStatusFlagDTO;
import org.skyer.goods.config.GoodsSwaggerApiConfig;
import org.skyer.goods.domain.repository.CategoryRepository;
import org.skyer.goods.domain.service.CategoryManageService;
import org.skyer.goods.domain.vo.CategoryTreeVO;
import org.skyer.goods.domain.vo.CategoryVO;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;

/**
 * 商品分类表 管理 API
 *
 * @author xurongfa 2021-12-24 09:12:57
 */
@RestController("categoryController.v1")
@RequestMapping("/v1/categorys")
@Api(tags = GoodsSwaggerApiConfig.GOODS_CATEGORY)
public class CategoryController extends BaseController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryManageService categoryManageService;

    @ApiOperation(value = "保存商品分类表")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody @Valid CategorySaveDTO category) {
        categoryManageService.save(category);
        return Results.success();
    }

//    @ApiOperation(value = "修改商品分类表")
//    @Permission(level = ResourceLevel.ORGANIZATION)
//    @PostMapping("/update")
//    public ResponseEntity<Category> update(@RequestBody Category category) {
//        categoryRepository.updateByPrimaryKeySelective(category);
//        return Results.success(category);
//    }
    @ApiOperation(value = "修改商品状态")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/update/status")
    public ResponseEntity<Void> updateByStatusFlag(
            @RequestBody @Valid CategoryUpdateByStatusFlagDTO categoryUpdateByStatusFlagDTO){
        categoryManageService.updateByStatusFlag(categoryUpdateByStatusFlagDTO);
        return Results.success();
    }

    @ApiOperation(value = "批量删除商品分类表")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @DeleteMapping("/batch/delete")
    public ResponseEntity<Void> batchDelete(
            @Encrypt @ApiParam(value = "商品分类ids", required = true) @RequestBody List<Long> ids){

        categoryManageService.batchDelete(ids);

        return Results.success();
    }

    @ApiOperation(value = "查询分类列表")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/page")
    public ResponseEntity<Page<CategoryVO>> page(
            @RequestBody CategoryQueryDTO categoryQueryDTO ){
        Page<CategoryVO> categoryPage = categoryManageService.pageIncludedAttr(categoryQueryDTO);
        return Results.success(categoryPage);
    }

    @ApiOperation(value = "获取分类树")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/tree")
    public ResponseEntity<List<CategoryTreeVO>> tree(
            @RequestBody CategoryTreeQueryDTO queryDTO){

        List<CategoryTreeVO> categoryTree = categoryManageService.getCategoryTree(queryDTO);

        return Results.success(categoryTree);
    }

}
