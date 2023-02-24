package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.domain.entity.ThirdPartyOrder;
import org.skyer.order.domain.repository.ThirdPartyOrderRepository;
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
 * 第三方平台订单 管理 API
 *
 * @author wushaochuan 2022-01-18 14:34:55
 */
@RestController("thirdPartyOrderSiteController.v1")
@RequestMapping("/v1/third-party-orders")
public class ThirdPartyOrderController extends BaseController {

    @Autowired
    private ThirdPartyOrderRepository thirdPartyOrderRepository;

    @ApiOperation(value = "第三方平台订单列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<ThirdPartyOrder>> list(ThirdPartyOrder thirdPartyOrder, @ApiIgnore @SortDefault(value = ThirdPartyOrder.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<ThirdPartyOrder> list = thirdPartyOrderRepository.pageAndSort(pageRequest, thirdPartyOrder);
        return Results.success(list);
    }

    @ApiOperation(value = "第三方平台订单明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<ThirdPartyOrder> detail(@PathVariable Long id) {
        ThirdPartyOrder thirdPartyOrder = thirdPartyOrderRepository.selectByPrimaryKey(id);
        return Results.success(thirdPartyOrder);
    }

    @ApiOperation(value = "创建第三方平台订单")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<ThirdPartyOrder> create(@RequestBody ThirdPartyOrder thirdPartyOrder) {
        validObject(thirdPartyOrder);
        thirdPartyOrderRepository.insertSelective(thirdPartyOrder);
        return Results.success(thirdPartyOrder);
    }

    @ApiOperation(value = "修改第三方平台订单")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<ThirdPartyOrder> update(@RequestBody ThirdPartyOrder thirdPartyOrder) {
        SecurityTokenHelper.validToken(thirdPartyOrder);
        thirdPartyOrderRepository.updateByPrimaryKeySelective(thirdPartyOrder);
        return Results.success(thirdPartyOrder);
    }

    @ApiOperation(value = "删除第三方平台订单")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody ThirdPartyOrder thirdPartyOrder) {
        SecurityTokenHelper.validToken(thirdPartyOrder);
        thirdPartyOrderRepository.deleteByPrimaryKey(thirdPartyOrder);
        return Results.success();
    }

}
