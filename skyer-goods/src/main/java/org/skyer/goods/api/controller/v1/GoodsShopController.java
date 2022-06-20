package org.skyer.goods.api.controller.v1;

import io.swagger.annotations.ApiOperation;
import org.skyer.core.base.BaseController;
import org.skyer.core.domain.Page;
import org.skyer.core.util.Results;
import org.skyer.goods.domain.entity.GoodsShop;
import org.skyer.goods.domain.service.GoodsShopService;
import org.skyer.mybatis.pagehelper.annotation.SortDefault;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 商品在店铺中的上下架关系表 管理 API
 *
 * @author chenzz
 * @date 2021-06-25 13:36:41
 */
@RestController("goodsShopController.v1")
@RequestMapping("/v1/goodsshops")
public class GoodsShopController extends BaseController {

    @Autowired
    private GoodsShopService goodsShopService;

    /**
     * 列表
     */
    @ApiOperation(value = "商品在店铺中的上下架关系表列表")
    @GetMapping
    public ResponseEntity<?> list(GoodsShop goodsShop, @ApiIgnore @SortDefault(value = GoodsShop.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<GoodsShop> list = goodsShopService.pageAndSort(pageRequest, goodsShop);
        return Results.success(list);
    }


    /**
     * 详细
     */
    @ApiOperation(value = "商品在店铺中的上下架关系表明细")
    @RequestMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        GoodsShop goodsShop = goodsShopService.selectByPrimaryKey(id);
        return Results.success(goodsShop);
    }

    /**
     * 创建
     */
    @ApiOperation(value = "创建商品在店铺中的上下架关系表")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody GoodsShop goodsShop) {
        goodsShopService.insertSelective(goodsShop);
        return Results.success(goodsShop);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改商品在店铺中的上下架关系表")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody GoodsShop goodsShop) {
        goodsShopService.updateByPrimaryKeySelective(goodsShop);
        return Results.success(goodsShop);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除商品在店铺中的上下架关系表")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        goodsShopService.deleteByPrimaryKey(id);
        return Results.success();
    }

}
