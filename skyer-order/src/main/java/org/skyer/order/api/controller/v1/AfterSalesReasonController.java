package org.skyer.order.api.controller.v1;

import org.skyer.core.exception.CommonException;
import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.app.service.AfterSalesReasonService;
import org.skyer.order.domain.entity.AfterSalesReason;
import org.skyer.order.domain.repository.AfterSalesReasonRepository;
import org.skyer.starter.keyencrypt.core.Encrypt;
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

import java.util.List;

/**
 * 售后原因 管理 API
 *
 * @author zhanglongbing@ebc.com 2022-03-14 10:41:33
 */
@RestController("afterSalesReasonSiteController.v1")
@RequestMapping("/v1/after-sales-reasons")
public class AfterSalesReasonController extends BaseController {

    @Autowired
    private AfterSalesReasonRepository afterSalesReasonRepository;
    @Autowired
    private AfterSalesReasonService afterSalesReasonService;

    @ApiOperation(value = "售后原因列表")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @GetMapping("/list")
    public ResponseEntity<List<AfterSalesReason>> list(Integer type) {
        List<AfterSalesReason> list = afterSalesReasonRepository.selectByCondition(Condition.builder(AfterSalesReason.class).andWhere(Sqls.custom()
        .andEqualTo(AfterSalesReason.FIELD_TYPE,type).andEqualTo(AfterSalesReason.FIELD_DELETE_FLAG,0)).build());
        return Results.success(list);
    }

    @ApiOperation(value = "售后原因分页")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @GetMapping("/page")
    public ResponseEntity<Page<AfterSalesReason>> page(AfterSalesReason afterSalesReason, @ApiIgnore @SortDefault(value = AfterSalesReason.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        afterSalesReason.setDeleteFlag(0);
        Page<AfterSalesReason> page = afterSalesReasonRepository.pageAndSort(pageRequest, afterSalesReason);
        return Results.success(page);
    }

    @ApiOperation(value = "售后原因明细")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @GetMapping("/detail/{id}")
    public ResponseEntity<AfterSalesReason> detail(@Encrypt@PathVariable Long id) {
        AfterSalesReason afterSalesReason = afterSalesReasonRepository.selectByPrimaryKey(id);
        return Results.success(afterSalesReason);
    }

    @ApiOperation(value = "创建售后原因")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @PostMapping("/create")
    public ResponseEntity<AfterSalesReason> create(@RequestBody AfterSalesReason afterSalesReason) {
        afterSalesReasonService.createAfterSalesReason(afterSalesReason);
        return Results.success(afterSalesReason);
    }

    @ApiOperation(value = "修改售后原因")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @PutMapping("/update")
    public ResponseEntity<AfterSalesReason> update(@Encrypt @RequestBody AfterSalesReason afterSalesReason) {
        afterSalesReasonService.updateAfterSalesReason(afterSalesReason);
        return Results.success(afterSalesReason);
    }

    @ApiOperation(value = "删除售后原因")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @DeleteMapping("/delete")
    public ResponseEntity<?> remove(@Encrypt @RequestBody AfterSalesReason afterSalesReason) {
       AfterSalesReason deleteAfterSalesReason =  afterSalesReasonRepository.selectByPrimaryKey(afterSalesReason);
       if (deleteAfterSalesReason.getUseFlag().equals(1)){
           throw new CommonException("已使用的原因不允许删除");
       }
        afterSalesReason.setDeleteFlag(1);
        afterSalesReasonRepository.updateByPrimaryKeySelective(afterSalesReason);
        return Results.success();
    }

}
