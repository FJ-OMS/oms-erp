package org.skyer.stock.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.stock.domain.entity.ShopWarehouse;

/**
 * 店铺和仓库关联表资源库
 *
 * @author lixiaoyang 2021-08-02 11:03:47
 */
public interface ShopWarehouseRepository extends BaseRepository<ShopWarehouse> {

    /**
     * 根据店铺ID获取渠道仓
     *
     * @param shopId 店铺ID
     * @author lixiaoyang
     * @date 2021/7/29 17:59
     */
    Long getChannelIdByShopId(Long shopId);
}
