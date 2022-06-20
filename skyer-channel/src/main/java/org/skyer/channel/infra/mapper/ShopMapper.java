package org.skyer.channel.infra.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.skyer.channel.api.dto.ChannelSaleOrderDTO;
import org.skyer.channel.api.dto.OnlineShopShowDTO;
import org.skyer.channel.api.dto.PhysicalShopShowDTO;
import org.skyer.channel.api.dto.QueryShopListDTO;
import org.skyer.channel.domain.entity.Shop;
import org.skyer.channel.domain.vo.*;
import org.skyer.mybatis.common.BaseMapper;

/**
 * Mapper
 *
 * @author lixiaoyang 2021-12-10 13:27:14
 */
public interface ShopMapper extends BaseMapper<Shop> {

    OnlineShopSettingVO getSetting(@Param("id") Long id);

    List<OnlineShopVO> getOnlineShopList(@Param("onlineShopShowDTO") OnlineShopShowDTO onlineShopShowDTO, @Param("tenantId") Long tenantId);

    List<PhysicalShopVO> getPhysicalShopList(@Param("physicalShopDTO") PhysicalShopShowDTO physicalShopShowDTO, @Param("tenantId") Long tenantId);


    List<ShopListVO> getShopList(@Param("dto") QueryShopListDTO dto, @Param("tenantId") Long tenantId);

    Shop getShopByNameAndCategoryTypeAndChannelType(@Param("name") String name, @Param("categoryType") String categoryType, @Param("channelType") String channelType, @Param("channelWarehouseName") String channelWarehouseName);

    List<ChannelSaleOrderVO>  queryChannelOrderSaleReport(@Param("dto") ChannelSaleOrderDTO dto);
}
