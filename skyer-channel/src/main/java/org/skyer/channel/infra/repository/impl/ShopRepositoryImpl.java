package org.skyer.channel.infra.repository.impl;

import org.skyer.channel.api.dto.ChannelSaleOrderDTO;
import org.skyer.channel.domain.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.skyer.channel.api.dto.OnlineShopShowDTO;
import org.skyer.channel.api.dto.PhysicalShopShowDTO;
import org.skyer.channel.api.dto.QueryShopListDTO;
import org.skyer.channel.domain.entity.Shop;
import org.skyer.channel.domain.repository.ShopRepository;
import org.skyer.channel.infra.mapper.ShopMapper;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

import java.util.List;

/**
 * 资源库实现
 *
 * @author lixiaoyang 2021-12-10 13:27:14
 */
@Component
public class ShopRepositoryImpl extends BaseRepositoryImpl<Shop> implements ShopRepository {
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public OnlineShopSettingVO getSetting(Long id) {
        return shopMapper.getSetting(id);
    }

    @Override
    public Page<OnlineShopVO> getPage(PageRequest pageRequest, OnlineShopShowDTO onlineShopShowDTO, Long tenantId) {
        return PageHelper.doPageAndSort(pageRequest, () -> shopMapper.getOnlineShopList(onlineShopShowDTO, tenantId));
    }

    @Override
    public Page<PhysicalShopVO> getPage(PageRequest pageRequest, PhysicalShopShowDTO physicalShopShowDTO, Long tenantId) {
        return PageHelper.doPageAndSort(pageRequest, () -> shopMapper.getPhysicalShopList(physicalShopShowDTO, tenantId));

    }

    @Override
    public Page<ShopListVO> queryShop(QueryShopListDTO dto, Long tenantId, PageRequest pageRequest) {
        return PageHelper.doPageAndSort(pageRequest, () -> shopMapper.getShopList(dto, tenantId));
    }

    @Override
    public Shop getShopByNameAndCategoryTypeAndChannelType(String name, String categoryType, String channelType, String channelWarehouseName) {
        return shopMapper.getShopByNameAndCategoryTypeAndChannelType(name, categoryType, channelType, channelWarehouseName);
    }

    @Override
    public List<ChannelSaleOrderVO> queryChannelOrderSaleReport(ChannelSaleOrderDTO dto) {
        return shopMapper.queryChannelOrderSaleReport(dto);
    }
}
