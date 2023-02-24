package org.skyer.stock.infra.mapper;

import org.apache.ibatis.annotations.Param;

import org.skyer.mybatis.common.BaseMapper;
import org.skyer.stock.domain.entity.ShopWarehouse;

/**
 * 店铺和仓库关联表Mapper
 *
 * @author lixiaoyang 2021-08-02 11:03:47
 */
public interface ShopWarehouseMapper extends BaseMapper<ShopWarehouse> {
    /**
     * 根据店铺ID获取渠道ID
     *
     * @author lixiaoyang
     * @date 2022/1/14 14:29
     */
    Long getChannelIdByShopId(@Param("shopId") Long shopId);
}
