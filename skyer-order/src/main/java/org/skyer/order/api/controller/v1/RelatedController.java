package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.domain.entity.Related;
import org.skyer.order.domain.repository.RelatedRepository;
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
 * 内部订单关联表 管理 API
 *
 * @author wushaochuan 2022-01-18 14:34:53
 */
@RestController("relatedSiteController.v1")
@RequestMapping("/v1/relateds")
public class RelatedController extends BaseController {

    @Autowired
    private RelatedRepository relatedRepository;

    @ApiOperation(value = "内部订单关联表列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<Related>> list(Related related, @ApiIgnore @SortDefault(value = Related.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<Related> list = relatedRepository.pageAndSort(pageRequest, related);
        return Results.success(list);
    }

    @ApiOperation(value = "内部订单关联表明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<Related> detail(@PathVariable Long id) {
        Related related = relatedRepository.selectByPrimaryKey(id);
        return Results.success(related);
    }

    @ApiOperation(value = "创建内部订单关联表")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<Related> create(@RequestBody Related related) {
        validObject(related);
        relatedRepository.insertSelective(related);
        return Results.success(related);
    }

    @ApiOperation(value = "修改内部订单关联表")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<Related> update(@RequestBody Related related) {
        SecurityTokenHelper.validToken(related);
        relatedRepository.updateByPrimaryKeySelective(related);
        return Results.success(related);
    }

    @ApiOperation(value = "删除内部订单关联表")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody Related related) {
        SecurityTokenHelper.validToken(related);
        relatedRepository.deleteByPrimaryKey(related);
        return Results.success();
    }

}
