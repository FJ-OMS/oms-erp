package org.skyer.order.infra.repository.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import java.util.Collections;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.domain.entity.ItemLine;
import org.skyer.order.domain.entity.ItemLinePrice;
import org.skyer.order.domain.repository.ItemLinePriceRepository;
import org.skyer.order.infra.mapper.ItemLineMapper;
import org.skyer.order.infra.mapper.ItemLinePriceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 内部订单商品行价格 资源库实现
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@Component
public class ItemLinePriceRepositoryImpl extends BaseRepositoryImpl<ItemLinePrice> implements ItemLinePriceRepository {
    @Autowired
    private ItemLinePriceMapper itemLinePriceMapper;

    @Override
    public Map<Long, Map<String, ItemLinePrice>> queryItemLinePriceMap(List<Long> itemLineId) {
        Map<Long, Map<String, ItemLinePrice>> resultMap = Maps.newConcurrentMap();
        List<ItemLinePrice> itemLinePrice = itemLinePriceMapper.selectByCondition(Condition.builder(ItemLinePrice.class).andWhere(Sqls.custom().andIn(ItemLinePrice.FIELD_ORDER_LINE_ID, itemLineId)).build());
        if (CollectionUtil.isEmpty(itemLinePrice)){
            return null;
        }
        Map<Long, List<ItemLinePrice>> itemPriceMap = itemLinePrice.stream().collect(Collectors.groupingBy(ItemLinePrice::getOrderLineId));
        itemPriceMap.forEach((lineId,list)-> {
            Map<String, ItemLinePrice> priceMap = list.stream().collect(Collectors.toMap(ItemLinePrice::getType, Function.identity(), (o1, o2) -> o1));
            resultMap.put(lineId,priceMap);
        });
        return resultMap;
    }

    @Override
    public List<ItemLinePrice> queryItemLineIdList(List<Long> itemLineId, String type) {
        List<ItemLinePrice> itemLinePrice = itemLinePriceMapper
                .selectByCondition(Condition.builder(ItemLinePrice.class)
                        .andWhere(Sqls.custom()
                                .andIn(ItemLinePrice.FIELD_ORDER_LINE_ID, itemLineId)
                                .andEqualTo(ItemLinePrice.FIELD_TYPE, type, true)
                        ).build());
        if (CollUtil.isEmpty(itemLinePrice)){
            return Collections.emptyList();
        }
        return itemLinePrice;
    }
}
