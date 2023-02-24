package org.skyer.goods.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.skyer.core.base.BaseController;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.goods.api.dto.CategoryAttributeQueryDTO;
import org.skyer.goods.config.GoodsSwaggerApiConfig;
import org.skyer.goods.domain.repository.CategoryAttributeRepository;
import org.skyer.goods.domain.vo.CategoryAttributeVO;
import org.skyer.swagger.annotation.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类属性表 管理 API
 *
 * @author xurongfa 2021-12-24 09:12:57
 */
@RestController("categoryAttributeController.v1")
@RequestMapping("/v1/category/attributes")
@Api(tags = GoodsSwaggerApiConfig.GOODS_CATEGORY_ATTRIBUTE)
public class CategoryAttributeController extends BaseController {

    @Autowired
    private CategoryAttributeRepository categoryAttributeRepository;

    @ApiModelProperty(value = "查询商品分类属性表")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/list/category/attribute")
    public ResponseEntity<List<CategoryAttributeVO>> queryCategoryAttribute(
            @RequestBody CategoryAttributeQueryDTO param){
        //控制 没有分类id 返回空
        if(ObjectUtils.isEmpty( param.getCategoryId() )){
            return Results.success(new ArrayList<>());
        }
        List<CategoryAttributeVO> categoryAttributeList = categoryAttributeRepository.listIncludedValue(param);
        return Results.success(categoryAttributeList);
    }

}
