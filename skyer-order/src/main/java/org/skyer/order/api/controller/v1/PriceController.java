package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.domain.entity.Price;
import org.skyer.order.domain.repository.PriceRepository;
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
 * 内部订单价格（聚合） 管理 API
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@RestController("priceSiteController.v1")
@RequestMapping("/v1/prices")
public class PriceController extends BaseController {

    @Autowired
    private PriceRepository priceRepository;

    @ApiOperation(value = "内部订单价格（聚合）列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<Price>> list(Price price, @ApiIgnore @SortDefault(value = Price.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<Price> list = priceRepository.pageAndSort(pageRequest, price);
        return Results.success(list);
    }

    @ApiOperation(value = "内部订单价格（聚合）明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<Price> detail(@PathVariable Long id) {
        Price price = priceRepository.selectByPrimaryKey(id);
        return Results.success(price);
    }

    @ApiOperation(value = "创建内部订单价格（聚合）")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<Price> create(@RequestBody Price price) {
        validObject(price);
        priceRepository.insertSelective(price);
        return Results.success(price);
    }

    @ApiOperation(value = "修改内部订单价格（聚合）")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<Price> update(@RequestBody Price price) {
        SecurityTokenHelper.validToken(price);
        priceRepository.updateByPrimaryKeySelective(price);
        return Results.success(price);
    }

    @ApiOperation(value = "删除内部订单价格（聚合）")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody Price price) {
        SecurityTokenHelper.validToken(price);
        priceRepository.deleteByPrimaryKey(price);
        return Results.success();
    }

}
