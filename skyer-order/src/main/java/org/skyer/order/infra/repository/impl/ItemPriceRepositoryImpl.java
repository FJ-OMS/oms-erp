package org.skyer.order.infra.repository.impl;

import cn.hutool.core.collection.CollectionUtil;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.api.vo.PriceTypeListVO;
import org.skyer.order.domain.entity.ItemLinePrice;
import org.skyer.order.domain.entity.ItemPrice;
import org.skyer.order.domain.repository.ItemPriceRepository;
import org.skyer.order.infra.enums.PriceTypeEnum;
import org.skyer.order.infra.mapper.ItemPriceMapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 内部订单明细价格（聚合） 资源库实现
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@Component
public class ItemPriceRepositoryImpl extends BaseRepositoryImpl<ItemPrice> implements ItemPriceRepository {
    @Autowired
    private ItemPriceMapper itemPriceMapper;

    @Override
    public Map<String, ItemPrice> queryItemPrice(Long itemId) {
        List<ItemPrice> itemPrices = itemPriceMapper.selectByCondition(Condition.builder(ItemPrice.class).andWhere(Sqls.custom().andEqualTo(ItemPrice.FIELD_ORDER_ITEM_ID, itemId)).build());
        if (CollectionUtil.isEmpty(itemPrices)){
            return null;
        }
        return itemPrices.stream().collect(Collectors.toMap(ItemPrice::getType, Function.identity(),(o1,o2)->o1));
    }

    @Override
    public Map<Long, Map<String, ItemPrice>> queryItemPriceMap(List<Long> idList) {
        Map<Long, Map<String, ItemPrice>> resultMap = Maps.newConcurrentMap();
        List<ItemPrice> itemPrice = itemPriceMapper.selectByCondition(Condition.builder(ItemPrice.class).andWhere(Sqls.custom().andIn(ItemPrice.FIELD_ORDER_ITEM_ID, idList)).build());
        if (CollectionUtil.isEmpty(itemPrice)){
            return null;
        }
        Map<Long, List<ItemPrice>> itemPriceMap = itemPrice.stream().collect(Collectors.groupingBy(ItemPrice::getOrderItemId));
        itemPriceMap.forEach((lineId,list)-> {
            Map<String, ItemPrice> priceMap = list.stream().collect(Collectors.toMap(ItemPrice::getType, Function.identity(), (o1, o2) -> o1));
            resultMap.put(lineId,priceMap);
        });
        return resultMap;
    }

    @Override
    public List<PriceTypeListVO> getPriceList() {
        List<PriceTypeListVO> list = Lists.newArrayList();
        PriceTypeListVO priceTypeListVO = null;
        for (PriceTypeEnum value : PriceTypeEnum.values()) {
            priceTypeListVO= new PriceTypeListVO();
            priceTypeListVO.setValue(value.getType());
            priceTypeListVO.setLabel(value.getTypeName());
            list.add(priceTypeListVO);
        }
        return list;
    }
}
