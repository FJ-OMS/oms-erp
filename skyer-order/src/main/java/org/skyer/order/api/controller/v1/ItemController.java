package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.repository.ItemRepository;
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
 * 内部订单商品明细 管理 API
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@RestController("itemSiteController.v1")
@RequestMapping("/v1/items")
public class ItemController extends BaseController {

    @Autowired
    private ItemRepository itemRepository;

    @ApiOperation(value = "内部订单商品明细列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<Item>> list(Item item, @ApiIgnore @SortDefault(value = Item.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<Item> list = itemRepository.pageAndSort(pageRequest, item);
        return Results.success(list);
    }

    @ApiOperation(value = "内部订单商品明细明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<Item> detail(@PathVariable Long id) {
        Item item = itemRepository.selectByPrimaryKey(id);
        return Results.success(item);
    }

    @ApiOperation(value = "创建内部订单商品明细")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item) {
        validObject(item);
        itemRepository.insertSelective(item);
        return Results.success(item);
    }

    @ApiOperation(value = "修改内部订单商品明细")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<Item> update(@RequestBody Item item) {
        SecurityTokenHelper.validToken(item);
        itemRepository.updateByPrimaryKeySelective(item);
        return Results.success(item);
    }

    @ApiOperation(value = "删除内部订单商品明细")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody Item item) {
        SecurityTokenHelper.validToken(item);
        itemRepository.deleteByPrimaryKey(item);
        return Results.success();
    }

}
