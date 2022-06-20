package org.skyer.stock.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.skyer.core.base.BaseController;
import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.stock.api.dto.StockQueryReqDTO;
import org.skyer.stock.config.StockSwaggerApiConfig;
import org.skyer.stock.domain.service.StockEsService;
import org.skyer.stock.domain.vo.GoodsStockResVO;
import org.skyer.swagger.annotation.Permission;

/**
 * 仓库库存表 管理 API
 *
 * @author lixiaoyang 2021-08-03 16:41:30
 */
@Api(tags = StockSwaggerApiConfig.GOODS_STOCK_MANAGE)
@RestController("goodsStockController.v1")
@RequestMapping("/v1/goods-stocks")
public class GoodsStockController extends BaseController {
    @Autowired
    private StockEsService stockEsService;

    @ApiOperation(value = "仓库库存表列表")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @PostMapping("/page")
    public ResponseEntity<Page<GoodsStockResVO>> pageStock(@RequestBody StockQueryReqDTO stockQueryReqDTO) {
        Page<GoodsStockResVO> goodsStockResVOS = stockEsService.queryStockEs(stockQueryReqDTO);
        return Results.success(goodsStockResVOS);
    }
}
