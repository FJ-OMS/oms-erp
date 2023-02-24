package org.skyer.stock.domain.service;

import java.util.List;

import org.skyer.core.domain.Page;
import org.skyer.stock.api.dto.GoodsStockDTO;
import org.skyer.stock.api.dto.StockQueryReqDTO;
import org.skyer.stock.domain.vo.GoodsStockResVO;

/**
 * 查询或者更新es里面的库存数据
 *
 * @description
 * @Author wusc
 * @create 2021/8/5 3:29 下午
 */
public interface StockEsService {
    /**
     * 将库存数据存入es
     *
     * @param goodsStockList
     */
    void createOrUpdateStockEs(List<GoodsStockDTO> goodsStockList, Long warehouseId);

    /**
     * 查询商品sku
     *
     * @param stockQueryReqDTO
     * @return
     */
    Page<GoodsStockResVO> queryStockEs(StockQueryReqDTO stockQueryReqDTO);

    /**
     * 删除es数据
     */
    void delStockEs(List<GoodsStockDTO> goodsStockList);

    /**
     * 清空库存所有记录
     */
    void delStockEsAll();
}
