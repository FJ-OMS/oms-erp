package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.domain.entity.Coupon;
import org.skyer.order.domain.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
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
 * 内部订单优惠券 管理 API
 *
 * @author wushaochuan 2022-01-18 14:34:53
 */
@RestController("couponSiteController.v1")
@RequestMapping("/v1/coupons")
public class CouponController extends BaseController {

    @Autowired
    private CouponRepository couponRepository;

    @ApiOperation(value = "内部订单优惠券列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<Coupon>> list(Coupon coupon, @ApiIgnore @SortDefault(value = Coupon.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<Coupon> list = couponRepository.pageAndSort(pageRequest, coupon);
        return Results.success(list);
    }

    @ApiOperation(value = "内部订单优惠券明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<Coupon> detail(@PathVariable Long id) {
        Coupon coupon = couponRepository.selectByPrimaryKey(id);
        return Results.success(coupon);
    }

    @ApiOperation(value = "创建内部订单优惠券")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<Coupon> create(@RequestBody Coupon coupon) {
        validObject(coupon);
        couponRepository.insertSelective(coupon);
        return Results.success(coupon);
    }

    @ApiOperation(value = "修改内部订单优惠券")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<Coupon> update(@RequestBody Coupon coupon) {
        SecurityTokenHelper.validToken(coupon);
        couponRepository.updateByPrimaryKeySelective(coupon);
        return Results.success(coupon);
    }

    @ApiOperation(value = "删除内部订单优惠券")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody Coupon coupon) {
        SecurityTokenHelper.validToken(coupon);
        couponRepository.deleteByPrimaryKey(coupon);
        return Results.success();
    }

}
