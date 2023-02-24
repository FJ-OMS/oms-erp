package org.skyer.channel.domain.repository;

import org.skyer.channel.api.dto.ChannelSaleOrderDTO;
import org.skyer.channel.api.dto.OnlineShopShowDTO;
import org.skyer.channel.api.dto.PhysicalShopShowDTO;
import org.skyer.channel.api.dto.QueryShopListDTO;
import org.skyer.channel.domain.entity.Shop;
import org.skyer.channel.domain.vo.*;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

import java.util.List;

/**
 * 资源库
 *
 * @author lixiaoyang 2021-12-10 13:27:14
 */
public interface ShopRepository extends BaseRepository<Shop> {

    OnlineShopSettingVO getSetting(Long id);

    Page<OnlineShopVO> getPage(PageRequest pageRequest, OnlineShopShowDTO onlineShopShowDTO, Long tenantId);

    Page<PhysicalShopVO> getPage(PageRequest pageRequest, PhysicalShopShowDTO physicalShopShowDTO, Long tenantId);


    Page<ShopListVO> queryShop(QueryShopListDTO dto, Long tenantId, PageRequest pageRequest);

    Shop getShopByNameAndCategoryTypeAndChannelType(String name, String categoryType, String channelType, String channelWarehouseName);


    List<ChannelSaleOrderVO> queryChannelOrderSaleReport(ChannelSaleOrderDTO dto);
}
