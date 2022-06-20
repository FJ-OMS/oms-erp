package org.skyer.goods.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.goods.api.dto.IdsReqDTO;
import org.skyer.goods.domain.entity.SpuAttribute;
import org.skyer.goods.domain.repository.SpuAttributeRepository;
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
 * 属性表 管理 API
 *
 * @author linzhuanghuang@ebc.com 2021-12-28 16:40:03
 */
@RestController("spuAttributeController.v1")
@RequestMapping("/v1/spu-attributes")
public class SpuAttributeController extends BaseController {

    @Autowired
    private SpuAttributeRepository spuAttributeRepository;

    @ApiOperation(value = "属性表列表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping
    public ResponseEntity<Page<SpuAttribute>> list(SpuAttribute spuAttribute, @ApiIgnore @SortDefault(value = SpuAttribute.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<SpuAttribute> list = spuAttributeRepository.pageAndSort(pageRequest, spuAttribute);
        return Results.success(list);
    }

    @ApiOperation(value = "属性表明细")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/{id}")
    public ResponseEntity<SpuAttribute> detail(@PathVariable Long id) {
        SpuAttribute spuAttribute = spuAttributeRepository.selectByPrimaryKey(id);
        return Results.success(spuAttribute);
    }

    @ApiOperation(value = "创建属性表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping
    public ResponseEntity<SpuAttribute> create(@RequestBody SpuAttribute spuAttribute) {
        validObject(spuAttribute);
        spuAttributeRepository.insertSelective(spuAttribute);
        return Results.success(spuAttribute);
    }

    @ApiOperation(value = "修改属性表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PutMapping
    public ResponseEntity<SpuAttribute> update(@RequestBody SpuAttribute spuAttribute) {
        SecurityTokenHelper.validToken(spuAttribute);
        spuAttributeRepository.updateByPrimaryKeySelective(spuAttribute);
        return Results.success(spuAttribute);
    }

    @ApiOperation(value = "删除属性表")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody SpuAttribute spuAttribute) {
        SecurityTokenHelper.validToken(spuAttribute);
        spuAttributeRepository.deleteByPrimaryKey(spuAttribute);
        return Results.success();
    }

    @ApiOperation(value = "删除商品属性")
    @PatchMapping("/batch-delete-attribute")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    public ResponseEntity<String> deleteByIds(@RequestBody IdsReqDTO idsReqDTO) {
        spuAttributeRepository.deleteAttribute(idsReqDTO.getIds());
        return Results.success("操作成功");
    }

}
