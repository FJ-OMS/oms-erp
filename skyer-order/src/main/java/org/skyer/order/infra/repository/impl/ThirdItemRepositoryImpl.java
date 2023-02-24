package org.skyer.order.infra.repository.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import java.util.Collections;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.domain.entity.ItemLine;
import org.skyer.order.domain.entity.ThirdItem;
import org.skyer.order.domain.repository.ThirdItemRepository;
import org.skyer.order.infra.mapper.ThirdItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 内部订单商品明细 资源库实现
 *
 * @author zhanglongbing@ebc.com 2022-04-20 10:15:56
 */
@Component
public class ThirdItemRepositoryImpl extends BaseRepositoryImpl<ThirdItem> implements ThirdItemRepository {
    @Autowired
    private ThirdItemMapper thirdItemMapper;

    @Override
    public Map<String, List<ThirdItem>> queryThirdItemByOuterNo(List<String> outerOrderList) {
        List<ThirdItem> itemLines = thirdItemMapper.selectByCondition(Condition.builder(ThirdItem.class).andWhere(Sqls.custom().andIn(ThirdItem.FIELD_OUTER_ORDER_NO, outerOrderList)).build());
        if (ObjectUtil.isEmpty(itemLines)) {
            return Collections.emptyMap();
        }
        return itemLines.stream().collect(Collectors.groupingBy(ThirdItem::getOuterOrderNo));
    }
}
