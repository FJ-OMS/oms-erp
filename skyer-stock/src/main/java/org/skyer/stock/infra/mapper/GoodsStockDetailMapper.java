package org.skyer.stock.infra.mapper;

import org.apache.ibatis.annotations.Param;

import org.skyer.mybatis.common.BaseMapper;
import org.skyer.stock.domain.entity.GoodsStockDetail;

/**
 * 仓库商品库存明细表Mapper
 *
 * @author lixiaoyang 2022-02-10 13:14:42
 */
public interface GoodsStockDetailMapper extends BaseMapper<GoodsStockDetail> {

    GoodsStockDetail getStockBySkuCodeAndWarehouseCode(@Param("warehouseCode") String warehouseCode,@Param("skuCode")  String skuCode);
}
