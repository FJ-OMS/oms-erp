package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.api.dto.AfterSaleItemLineDTO;
import org.skyer.order.api.vo.AfterSaleItemLineVO;
import org.skyer.order.domain.entity.AfterSaleItemLine;
import org.skyer.order.domain.repository.AfterSaleItemLineRepository;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.skyer.core.iam.ResourceLevel;
import org.skyer.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * 售后单商品行 管理 API
 *
 * @author lzh 2022-03-16 10:55:40
 */
@RestController("afterSaleItemLineSiteController.v1")
@RequestMapping("/v1/after-sale-item-lines")
public class AfterSaleItemLineController extends BaseController {

    @Autowired
    private AfterSaleItemLineRepository afterSaleItemLineRepository;

    @ApiOperation(value = "查询可退款商品行")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @GetMapping("/refund-only-item-list/{itemId}")
    public ResponseEntity<List<AfterSaleItemLineVO>> returnLisByItemId(@PathVariable @Encrypt Long itemId) {
        List<AfterSaleItemLineVO> list = afterSaleItemLineRepository.returnLisByItemId(itemId);
        return Results.success(list);
    }

    @ApiOperation(value = "查询可退货商品行")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @GetMapping("/refund-goods-item-list/{itemId}")
    public ResponseEntity<List<AfterSaleItemLineVO>> returnGoodsListByItemId(@PathVariable @Encrypt Long itemId) {
        List<AfterSaleItemLineVO> list = afterSaleItemLineRepository.returnGoodsListByItemId(itemId);
        return Results.success(list);
    }

    @ApiOperation(value = "详情页展开商品行")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @PostMapping("/item-line-details")
    public ResponseEntity<List<AfterSaleItemLine>> itemLineDetails(@RequestBody AfterSaleItemLine afterSaleItemLine) {
        List<AfterSaleItemLine> list =  afterSaleItemLineRepository.itemLineDetails(afterSaleItemLine);
        return Results.success(list);
    }

}
