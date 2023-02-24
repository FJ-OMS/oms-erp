package org.skyer.stock.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.stock.domain.entity.GoodsStockDetail;
import org.skyer.stock.domain.repository.GoodsStockDetailRepository;
import org.skyer.stock.infra.mapper.GoodsStockDetailMapper;

/**
 * 仓库商品库存明细表 资源库实现
 *
 * @author lixiaoyang 2022-02-10 13:14:42
 */
@Component
public class GoodsStockDetailRepositoryImpl extends BaseRepositoryImpl<GoodsStockDetail> implements GoodsStockDetailRepository {
    @Autowired
    private GoodsStockDetailMapper goodsStockDetailMapper;

    @Override
    public GoodsStockDetail getStockBySkuCodeAndWarehouseCode(String warehouseCode, String skuCode) {
        return goodsStockDetailMapper.getStockBySkuCodeAndWarehouseCode(warehouseCode,skuCode);
    }
}
