package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.domain.entity.ItemLinePrice;
import org.skyer.order.domain.repository.ItemLinePriceRepository;
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
 * 内部订单商品行价格 管理 API
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@RestController("itemLinePriceSiteController.v1")
@RequestMapping("/v1/item-line-prices")
public class ItemLinePriceController extends BaseController {

    @Autowired
    private ItemLinePriceRepository itemLinePriceRepository;

    @ApiOperation(value = "内部订单商品行价格列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<ItemLinePrice>> list(ItemLinePrice itemLinePrice, @ApiIgnore @SortDefault(value = ItemLinePrice.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<ItemLinePrice> list = itemLinePriceRepository.pageAndSort(pageRequest, itemLinePrice);
        return Results.success(list);
    }

    @ApiOperation(value = "内部订单商品行价格明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<ItemLinePrice> detail(@PathVariable Long id) {
        ItemLinePrice itemLinePrice = itemLinePriceRepository.selectByPrimaryKey(id);
        return Results.success(itemLinePrice);
    }

    @ApiOperation(value = "创建内部订单商品行价格")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<ItemLinePrice> create(@RequestBody ItemLinePrice itemLinePrice) {
        validObject(itemLinePrice);
        itemLinePriceRepository.insertSelective(itemLinePrice);
        return Results.success(itemLinePrice);
    }

    @ApiOperation(value = "修改内部订单商品行价格")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<ItemLinePrice> update(@RequestBody ItemLinePrice itemLinePrice) {
        SecurityTokenHelper.validToken(itemLinePrice);
        itemLinePriceRepository.updateByPrimaryKeySelective(itemLinePrice);
        return Results.success(itemLinePrice);
    }

    @ApiOperation(value = "删除内部订单商品行价格")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody ItemLinePrice itemLinePrice) {
        SecurityTokenHelper.validToken(itemLinePrice);
        itemLinePriceRepository.deleteByPrimaryKey(itemLinePrice);
        return Results.success();
    }

}
