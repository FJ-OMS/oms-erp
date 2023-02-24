package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.api.vo.PriceTypeListVO;
import org.skyer.order.domain.entity.ItemPrice;
import org.skyer.order.domain.repository.ItemPriceRepository;
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
 * 内部订单明细价格（聚合） 管理 API
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@RestController("itemPriceSiteController.v1")
@RequestMapping("/v1/item-prices")
public class ItemPriceController extends BaseController {

    @Autowired
    private ItemPriceRepository itemPriceRepository;

    @ApiOperation(value = "内部订单明细价格（聚合）列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<ItemPrice>> list(ItemPrice itemPrice, @ApiIgnore @SortDefault(value = ItemPrice.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<ItemPrice> list = itemPriceRepository.pageAndSort(pageRequest, itemPrice);
        return Results.success(list);
    }

    @ApiOperation(value = "内部订单明细价格（聚合）明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<ItemPrice> detail(@PathVariable Long id) {
        ItemPrice itemPrice = itemPriceRepository.selectByPrimaryKey(id);
        return Results.success(itemPrice);
    }

    @ApiOperation(value = "创建内部订单明细价格（聚合）")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<ItemPrice> create(@RequestBody ItemPrice itemPrice) {
        validObject(itemPrice);
        itemPriceRepository.insertSelective(itemPrice);
        return Results.success(itemPrice);
    }

    @ApiOperation(value = "修改内部订单明细价格（聚合）")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<ItemPrice> update(@RequestBody ItemPrice itemPrice) {
        SecurityTokenHelper.validToken(itemPrice);
        itemPriceRepository.updateByPrimaryKeySelective(itemPrice);
        return Results.success(itemPrice);
    }

    @ApiOperation(value = "删除内部订单明细价格（聚合）")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody ItemPrice itemPrice) {
        SecurityTokenHelper.validToken(itemPrice);
        itemPriceRepository.deleteByPrimaryKey(itemPrice);
        return Results.success();
    }

    @ApiOperation(value = "获取价格枚举")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("price-type")
    public ResponseEntity<List<PriceTypeListVO>> getPriceList() {
        List<PriceTypeListVO> vos=itemPriceRepository.getPriceList();
        return Results.success(vos);
    }

}
