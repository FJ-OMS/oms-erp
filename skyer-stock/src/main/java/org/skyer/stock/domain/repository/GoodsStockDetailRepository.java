package org.skyer.stock.domain.repository;


import org.skyer.mybatis.base.BaseRepository;
import org.skyer.stock.domain.entity.GoodsStockDetail;

/**
 * 仓库商品库存明细表资源库
 *
 * @author lixiaoyang 2022-02-10 13:14:42
 */
public interface GoodsStockDetailRepository extends BaseRepository<GoodsStockDetail> {
    /**
     *  获取库存
     */
    GoodsStockDetail getStockBySkuCodeAndWarehouseCode(String warehouseCode,String skuCode);
}
