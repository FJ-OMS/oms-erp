package org.skyer.goods.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.goods.domain.entity.SpuAttributeValue;
import org.skyer.goods.domain.repository.SpuAttributeValueRepository;
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
 * 属性值表 管理 API
 *
 * @author linzhuanghuang@ebc.com 2021-12-28 16:40:03
 */
@RestController("spuAttributeValueController.v1")
@RequestMapping("/v1/spu-attribute-values")
public class SpuAttributeValueController extends BaseController {

    @Autowired
    private SpuAttributeValueRepository spuAttributeValueRepository;

    @ApiOperation(value = "属性值表列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    public ResponseEntity<Page<SpuAttributeValue>> list(SpuAttributeValue spuAttributeValue, @ApiIgnore @SortDefault(value = SpuAttributeValue.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<SpuAttributeValue> list = spuAttributeValueRepository.pageAndSort(pageRequest, spuAttributeValue);
        return Results.success(list);
    }

    @ApiOperation(value = "属性值表明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/{id}")
    public ResponseEntity<SpuAttributeValue> detail(@PathVariable Long id) {
        SpuAttributeValue spuAttributeValue = spuAttributeValueRepository.selectByPrimaryKey(id);
        return Results.success(spuAttributeValue);
    }

    @ApiOperation(value = "创建属性值表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<SpuAttributeValue> create(@RequestBody SpuAttributeValue spuAttributeValue) {
        validObject(spuAttributeValue);
        spuAttributeValueRepository.insertSelective(spuAttributeValue);
        return Results.success(spuAttributeValue);
    }

    @ApiOperation(value = "修改属性值表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping
    public ResponseEntity<SpuAttributeValue> update(@RequestBody SpuAttributeValue spuAttributeValue) {
        SecurityTokenHelper.validToken(spuAttributeValue);
        spuAttributeValueRepository.updateByPrimaryKeySelective(spuAttributeValue);
        return Results.success(spuAttributeValue);
    }

    @ApiOperation(value = "删除属性值表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody SpuAttributeValue spuAttributeValue) {
        SecurityTokenHelper.validToken(spuAttributeValue);
        spuAttributeValueRepository.deleteByPrimaryKey(spuAttributeValue);
        return Results.success();
    }

}
