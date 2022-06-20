package org.skyer.goods.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.goods.domain.entity.SkuDraft;
import org.skyer.goods.domain.repository.SkuDraftRepository;
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
 * 商品规格草稿表 管理 API
 *
 * @author zhanglongbing@ebc.com 2021-12-29 11:12:15
 */
@RestController("skuDraftSiteController.v1")
@RequestMapping("/v1/sku-drafts")
public class SkuDraftController extends BaseController {

    @Autowired
    private SkuDraftRepository skuDraftRepository;

    @ApiOperation(value = "商品规格草稿表列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<SkuDraft>> list(SkuDraft skuDraft, @ApiIgnore @SortDefault(value = SkuDraft.FIELD_SKU_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<SkuDraft> list = skuDraftRepository.pageAndSort(pageRequest, skuDraft);
        return Results.success(list);
    }

    @ApiOperation(value = "商品规格草稿表明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{skuId}")
    public ResponseEntity<SkuDraft> detail(@PathVariable Long skuId) {
        SkuDraft skuDraft = skuDraftRepository.selectByPrimaryKey(skuId);
        return Results.success(skuDraft);
    }

    @ApiOperation(value = "创建商品规格草稿表")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<SkuDraft> create(@RequestBody SkuDraft skuDraft) {
        validObject(skuDraft);
        skuDraftRepository.insertSelective(skuDraft);
        return Results.success(skuDraft);
    }

    @ApiOperation(value = "修改商品规格草稿表")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<SkuDraft> update(@RequestBody SkuDraft skuDraft) {
        skuDraftRepository.updateByPrimaryKeySelective(skuDraft);
        return Results.success(skuDraft);
    }

    @ApiOperation(value = "删除商品规格草稿表")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody SkuDraft skuDraft) {
        skuDraftRepository.deleteByPrimaryKey(skuDraft);
        return Results.success();
    }

}
