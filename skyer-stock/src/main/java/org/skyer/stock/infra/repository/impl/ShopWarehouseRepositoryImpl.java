package org.skyer.stock.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.stock.domain.entity.ShopWarehouse;
import org.skyer.stock.domain.repository.ShopWarehouseRepository;
import org.skyer.stock.infra.mapper.ShopWarehouseMapper;

/**
 * 店铺和仓库关联表 资源库实现
 *
 * @author lixiaoyang 2021-08-02 11:03:47
 */
@Component
public class ShopWarehouseRepositoryImpl extends BaseRepositoryImpl<ShopWarehouse> implements ShopWarehouseRepository {
    @Autowired
    private ShopWarehouseMapper shopWarehouseMapper;

    @Override
    public Long getChannelIdByShopId(Long shopId) {
        return shopWarehouseMapper.getChannelIdByShopId(shopId);
    }
}
