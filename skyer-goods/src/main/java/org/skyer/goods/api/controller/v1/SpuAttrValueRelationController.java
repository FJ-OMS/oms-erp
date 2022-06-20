package org.skyer.goods.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.goods.domain.entity.SpuAttrValueRelation;
import org.skyer.goods.domain.repository.SpuAttrValueRelationRepository;
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
 * 商品和属性值关联关系 管理 API
 *
 * @author wushaochuan 2021-07-19 09:18:33
 */
@RestController("goodsAttrValueRelationController.v1")
@RequestMapping("/v1/{organizationId}/goods-attr-value-relations")
public class SpuAttrValueRelationController extends BaseController {

    @Autowired
    private SpuAttrValueRelationRepository spuAttrValueRelationRepository;

    @ApiOperation(value = "商品和属性值关联关系列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    public ResponseEntity<Page<SpuAttrValueRelation>> list(SpuAttrValueRelation spuAttrValueRelation, @ApiIgnore @SortDefault(value = SpuAttrValueRelation.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<SpuAttrValueRelation> list = spuAttrValueRelationRepository.pageAndSort(pageRequest, spuAttrValueRelation);
        return Results.success(list);
    }

    @ApiOperation(value = "商品和属性值关联关系明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/{id}")
    public ResponseEntity<SpuAttrValueRelation> detail(@PathVariable Long id) {
        SpuAttrValueRelation spuAttrValueRelation = spuAttrValueRelationRepository.selectByPrimaryKey(id);
        return Results.success(spuAttrValueRelation);
    }

    @ApiOperation(value = "创建商品和属性值关联关系")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<SpuAttrValueRelation> create(@RequestBody SpuAttrValueRelation spuAttrValueRelation) {
        validObject(spuAttrValueRelation);
        spuAttrValueRelationRepository.insertSelective(spuAttrValueRelation);
        return Results.success(spuAttrValueRelation);
    }

    @ApiOperation(value = "修改商品和属性值关联关系")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping
    public ResponseEntity<SpuAttrValueRelation> update(@RequestBody SpuAttrValueRelation spuAttrValueRelation) {
        SecurityTokenHelper.validToken(spuAttrValueRelation);
        spuAttrValueRelationRepository.updateByPrimaryKeySelective(spuAttrValueRelation);
        return Results.success(spuAttrValueRelation);
    }

    @ApiOperation(value = "删除商品和属性值关联关系")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody SpuAttrValueRelation spuAttrValueRelation) {
        SecurityTokenHelper.validToken(spuAttrValueRelation);
        spuAttrValueRelationRepository.deleteByPrimaryKey(spuAttrValueRelation);
        return Results.success();
    }

}
