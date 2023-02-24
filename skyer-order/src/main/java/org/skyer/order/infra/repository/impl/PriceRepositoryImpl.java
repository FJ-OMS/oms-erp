package org.skyer.order.infra.repository.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import java.util.Collections;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.domain.entity.Price;
import org.skyer.order.domain.repository.PriceRepository;
import org.skyer.order.infra.mapper.PriceMapper;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 内部订单价格（聚合） 资源库实现
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@Component
public class PriceRepositoryImpl extends BaseRepositoryImpl<Price> implements PriceRepository {
    @Autowired
    private PriceMapper priceMapper;

    @Override
    public Map<String, Price> queryOrderPrice(String innerOrderNo) {
        List<Price> prices = priceMapper.selectByCondition(Condition.builder(Price.class).andWhere(Sqls.custom().andEqualTo(Price.FIELD_INNER_ORDER_NO, innerOrderNo)).build());
        if (CollectionUtil.isEmpty(prices)) {
            return null;
        }
        return prices.stream().collect(Collectors.toMap(Price::getType, Function.identity(), (o1, o2) -> o1));
    }

    @Override
    public List<Price> queryOrderPriceByList(List<String> innerOrderNoList) {
        List<Price> prices = priceMapper.selectByCondition(Condition.builder(Price.class)
                .andWhere(Sqls.custom()
                        .andIn(Price.FIELD_INNER_ORDER_NO, innerOrderNoList))
                .build());
        if (CollUtil.isEmpty(prices)) {
            return Collections.emptyList();
        }
        return prices;
    }
}
