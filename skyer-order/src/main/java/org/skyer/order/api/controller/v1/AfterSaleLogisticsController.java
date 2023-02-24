package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.domain.entity.AfterSaleLogistics;
import org.skyer.order.domain.repository.AfterSaleLogisticsRepository;
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
 *  管理 API
 *
 * @author lzh 2022-03-18 16:26:17
 */
@RestController("afterSaleLogisticsSiteController.v1")
@RequestMapping("/v1/after-sale-logisticss")
public class AfterSaleLogisticsController extends BaseController {

    @Autowired
    private AfterSaleLogisticsRepository afterSaleLogisticsRepository;

    @ApiOperation(value = "列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<AfterSaleLogistics>> list(AfterSaleLogistics afterSaleLogistics, @ApiIgnore @SortDefault(value = AfterSaleLogistics.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<AfterSaleLogistics> list = afterSaleLogisticsRepository.pageAndSort(pageRequest, afterSaleLogistics);
        return Results.success(list);
    }

    @ApiOperation(value = "明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<AfterSaleLogistics> detail(@PathVariable Long id) {
        AfterSaleLogistics afterSaleLogistics = afterSaleLogisticsRepository.selectByPrimaryKey(id);
        return Results.success(afterSaleLogistics);
    }

    @ApiOperation(value = "创建")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<AfterSaleLogistics> create(@RequestBody AfterSaleLogistics afterSaleLogistics) {
        validObject(afterSaleLogistics);
        afterSaleLogisticsRepository.insertSelective(afterSaleLogistics);
        return Results.success(afterSaleLogistics);
    }

    @ApiOperation(value = "修改")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<AfterSaleLogistics> update(@RequestBody AfterSaleLogistics afterSaleLogistics) {
        SecurityTokenHelper.validToken(afterSaleLogistics);
        afterSaleLogisticsRepository.updateByPrimaryKeySelective(afterSaleLogistics);
        return Results.success(afterSaleLogistics);
    }

    @ApiOperation(value = "删除")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody AfterSaleLogistics afterSaleLogistics) {
        SecurityTokenHelper.validToken(afterSaleLogistics);
        afterSaleLogisticsRepository.deleteByPrimaryKey(afterSaleLogistics);
        return Results.success();
    }

}
