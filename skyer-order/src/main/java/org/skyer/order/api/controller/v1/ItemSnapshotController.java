package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.domain.entity.ItemSnapshot;
import org.skyer.order.domain.repository.ItemSnapshotRepository;
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
 * 内部订单商品明细快照 管理 API
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@RestController("itemSnapshotSiteController.v1")
@RequestMapping("/v1/item-snapshots")
public class ItemSnapshotController extends BaseController {

    @Autowired
    private ItemSnapshotRepository itemSnapshotRepository;

    @ApiOperation(value = "内部订单商品明细快照列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<ItemSnapshot>> list(ItemSnapshot itemSnapshot, @ApiIgnore @SortDefault(value = ItemSnapshot.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<ItemSnapshot> list = itemSnapshotRepository.pageAndSort(pageRequest, itemSnapshot);
        return Results.success(list);
    }

    @ApiOperation(value = "内部订单商品明细快照明细")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<ItemSnapshot> detail(@PathVariable Long id) {
        ItemSnapshot itemSnapshot = itemSnapshotRepository.selectByPrimaryKey(id);
        return Results.success(itemSnapshot);
    }

    @ApiOperation(value = "创建内部订单商品明细快照")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<ItemSnapshot> create(@RequestBody ItemSnapshot itemSnapshot) {
        validObject(itemSnapshot);
        itemSnapshotRepository.insertSelective(itemSnapshot);
        return Results.success(itemSnapshot);
    }

    @ApiOperation(value = "修改内部订单商品明细快照")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<ItemSnapshot> update(@RequestBody ItemSnapshot itemSnapshot) {
        SecurityTokenHelper.validToken(itemSnapshot);
        itemSnapshotRepository.updateByPrimaryKeySelective(itemSnapshot);
        return Results.success(itemSnapshot);
    }

    @ApiOperation(value = "删除内部订单商品明细快照")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody ItemSnapshot itemSnapshot) {
        SecurityTokenHelper.validToken(itemSnapshot);
        itemSnapshotRepository.deleteByPrimaryKey(itemSnapshot);
        return Results.success();
    }

}
