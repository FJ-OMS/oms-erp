package org.skyer.goods.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.goods.domain.entity.SkuAttributeValue;
import org.skyer.goods.domain.repository.SkuAttributeValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.skyer.mybatis.helper.SecurityTokenHelper;

import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.mybatis.pagehelper.annotation.SortDefault;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.skyer.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * sku属性关联表 管理 API
 *
 * @author zhanglongbing@ebc.com 2021-12-28 09:17:10
 */
@RestController("skuAttributeValueSiteController.v1")
@RequestMapping("/v1/sku-attribute-values")
public class SkuAttributeValueController extends BaseController {

    @Autowired
    private SkuAttributeValueRepository skuAttributeValueRepository;

    @ApiOperation(value = "sku属性关联表列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<SkuAttributeValue>> list(SkuAttributeValue skuAttributeValue, @ApiIgnore @SortDefault(value = SkuAttributeValue.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<SkuAttributeValue> list = skuAttributeValueRepository.pageAndSort(pageRequest, skuAttributeValue);
        return Results.success(list);
    }

    @ApiOperation(value = "sku属性关联表明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<SkuAttributeValue> detail(@PathVariable Long id) {
        SkuAttributeValue skuAttributeValue = skuAttributeValueRepository.selectByPrimaryKey(id);
        return Results.success(skuAttributeValue);
    }

    @ApiOperation(value = "创建sku属性关联表")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<SkuAttributeValue> create(@RequestBody SkuAttributeValue skuAttributeValue) {
        validObject(skuAttributeValue);
        skuAttributeValueRepository.insertSelective(skuAttributeValue);
        return Results.success(skuAttributeValue);
    }

    @ApiOperation(value = "修改sku属性关联表")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<SkuAttributeValue> update(@RequestBody SkuAttributeValue skuAttributeValue) {
        skuAttributeValueRepository.updateByPrimaryKeySelective(skuAttributeValue);
        return Results.success(skuAttributeValue);
    }

    @ApiOperation(value = "删除sku属性关联表")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody SkuAttributeValue skuAttributeValue) {
        skuAttributeValueRepository.deleteByPrimaryKey(skuAttributeValue);
        return Results.success();
    }

}
