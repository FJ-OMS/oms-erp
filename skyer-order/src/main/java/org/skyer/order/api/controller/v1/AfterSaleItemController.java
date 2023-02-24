package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.api.dto.AfterSaleItemDTO;
import org.skyer.order.api.vo.AfterSaleItemVO;
import org.skyer.order.domain.repository.AfterSaleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

/**
 * 售后单商品 管理 API
 *
 * @author lzh 2022-03-16 10:55:40
 */
@RestController("afterSaleItemSiteController.v1")
@RequestMapping("/v1/after-sale-items")
public class AfterSaleItemController extends BaseController {

    @Autowired
    private AfterSaleItemRepository afterSaleItemRepository;

    @ApiOperation(value = "根据innerNo查询可退款/退货item")
    @Permission(level = ResourceLevel.SITE,permissionLogin = true)
    @PostMapping("/refund-page-by-innerNo")
    public ResponseEntity<Page<AfterSaleItemVO>> returnPageByInnerNo(@RequestBody @Valid AfterSaleItemDTO afterSaleItemDto) {
        Page<AfterSaleItemVO> page = afterSaleItemRepository.returnPageByInnerNo(afterSaleItemDto);
        return Results.success(page);
    }



}
