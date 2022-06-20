package org.skyer.goods.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.skyer.core.base.BaseController;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.goods.api.dto.*;
import org.skyer.goods.app.service.SkuEsService;
import org.skyer.goods.app.service.SkuService;
import org.skyer.goods.config.GoodsSwaggerApiConfig;
import org.skyer.goods.domain.vo.GoodsSkuDetailVO;
import org.skyer.goods.domain.vo.GoodsSkuPageVO;
import org.skyer.goods.domain.vo.RpcGoodsResVO;
import org.skyer.goods.domain.vo.RpcOrderResVO;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.swagger.annotation.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 商品sku表 管理 API
 *
 * @author zhanglb 2021-12-28
 */
@RestController("goodsSkuController.v1")
@RequestMapping("/v1/goods/sku")
@Api(tags = GoodsSwaggerApiConfig.GOODS_SKU)
public class GoodsSkuController extends BaseController {

    @Autowired
    private SkuService skuService;

    @Autowired
    private SkuEsService skuEsService;


    @ApiOperation(value = "商品sku列表(分页)")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping("/page")
    public ResponseEntity<Page<GoodsSkuPageVO>> list(@Valid @RequestBody GoodsSkuPageDTO goodsSkuPageDTO) throws Exception {
        Page<GoodsSkuPageVO> list = skuEsService.listPage(goodsSkuPageDTO);
        return Results.success(list);
    }

    @ApiOperation(value = "sku明细")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/{id}")
    public ResponseEntity<GoodsSkuDetailVO> detail(@Encrypt @PathVariable Long id,boolean isEdit) {
        GoodsSkuDetailVO goodsSku = skuService.getDetail(id,isEdit);
        return Results.success(goodsSku);
    }

    @ApiOperation(value = "根据商品id获取已经生成的sku")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/goods/{spuId}")
    public ResponseEntity<List<GoodsSkuDetailVO>> goodsSkuDetail(@PathVariable @Encrypt Long spuId) {
        List<GoodsSkuDetailVO> list = skuService.getSkuAndAttributeDataByGoodsId(spuId);
        return Results.success(list);
    }

    @ApiOperation(value = "删除商品sku(多个)")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @DeleteMapping
    public ResponseEntity<?> deleteByIds(@ApiParam("主键ID") @RequestBody IdsReqDTO idsReqDTO) {
        skuService.deleteByIds(idsReqDTO.getIds());
        return Results.success("操作成功");
    }

    @ApiOperation(value = "创建商品sku")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody SkuBatchSaveDTO skuBatchSaveDTO) {
        skuService.saveSku(skuBatchSaveDTO);
        return Results.success("操作成功");
    }

    @ApiOperation(value = "编辑sku")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody SkuUpdateDTO updateDTO) {
        skuService.updateSku(updateDTO);
        return Results.success("编辑成功");
    }

    @ApiModelProperty(value = "根据skuCode查询sku--用于远程调用")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @PostMapping("/list/goods")
    public ResponseEntity<List<RpcGoodsResVO>> getGoodsList(@RequestBody List<String> skuCodeList) {
        return Results.success(skuService.queryRPCGoodsList(skuCodeList));
    }


    @ApiModelProperty(value = "套餐下商品的List")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @PostMapping("/meals-goods-list")
    public ResponseEntity<List<RpcOrderResVO>> getMealsGoodsList(@RequestBody List<String> skuCodeList) {
        return Results.success(skuService.getMealsGoodsList(skuCodeList));
    }

    @ApiModelProperty(value = "根据skuCode查询订单skuVO--用于远程调用")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @PostMapping("/order/sku/list")
    public ResponseEntity<List<RpcOrderResVO>> queryOrderRpcList(@RequestBody List<String> skuCodeList) {
        return Results.success(skuService.queryOrderRpcList(skuCodeList));
    }

    @ApiOperation(value = "批量提交审批")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PutMapping("/batchAudit")
    public ResponseEntity<?> batchAudit(@RequestBody SkuBatchAuditDTO skuBatchAuditDTO) {
        skuService.batchAudit(skuBatchAuditDTO);
        return Results.success("提交成功");
    }

    @ApiOperation(value = "刷新es数据")
    @GetMapping("/es-test")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionPublic = true)
    public ResponseEntity<Void> esTest() throws Exception {
        skuEsService.delSkuEsAll();
        skuEsService.skuStatusUp();
        return Results.success();
    }


}
