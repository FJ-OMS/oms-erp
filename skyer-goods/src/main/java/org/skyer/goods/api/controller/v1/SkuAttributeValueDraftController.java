package org.skyer.goods.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.goods.domain.entity.SkuAttributeValueDraft;
import org.skyer.goods.domain.repository.SkuAttributeValueDraftRepository;
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
 * sku属性关联草稿表 管理 API
 *
 * @author zhanglongbing@ebc.com 2021-12-29 11:23:43
 */
@RestController("skuAttributeValueDraftSiteController.v1")
@RequestMapping("/v1/sku-attribute-value-drafts")
public class SkuAttributeValueDraftController extends BaseController {

    @Autowired
    private SkuAttributeValueDraftRepository skuAttributeValueDraftRepository;

    @ApiOperation(value = "sku属性关联草稿表列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<SkuAttributeValueDraft>> list(SkuAttributeValueDraft skuAttributeValueDraft, @ApiIgnore @SortDefault(value = SkuAttributeValueDraft.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<SkuAttributeValueDraft> list = skuAttributeValueDraftRepository.pageAndSort(pageRequest, skuAttributeValueDraft);
        return Results.success(list);
    }

    @ApiOperation(value = "sku属性关联草稿表明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<SkuAttributeValueDraft> detail(@PathVariable Long id) {
        SkuAttributeValueDraft skuAttributeValueDraft = skuAttributeValueDraftRepository.selectByPrimaryKey(id);
        return Results.success(skuAttributeValueDraft);
    }

    @ApiOperation(value = "创建sku属性关联草稿表")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<SkuAttributeValueDraft> create(@RequestBody SkuAttributeValueDraft skuAttributeValueDraft) {
        validObject(skuAttributeValueDraft);
        skuAttributeValueDraftRepository.insertSelective(skuAttributeValueDraft);
        return Results.success(skuAttributeValueDraft);
    }

    @ApiOperation(value = "修改sku属性关联草稿表")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<SkuAttributeValueDraft> update(@RequestBody SkuAttributeValueDraft skuAttributeValueDraft) {
        skuAttributeValueDraftRepository.updateByPrimaryKeySelective(skuAttributeValueDraft);
        return Results.success(skuAttributeValueDraft);
    }

    @ApiOperation(value = "删除sku属性关联草稿表")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody SkuAttributeValueDraft skuAttributeValueDraft) {
        skuAttributeValueDraftRepository.deleteByPrimaryKey(skuAttributeValueDraft);
        return Results.success();
    }

}
