package org.skyer.order.infra.repository.impl;

import org.apache.commons.collections.CollectionUtils;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.domain.entity.ItemSnapshot;
import org.skyer.order.domain.repository.ItemSnapshotRepository;
import org.skyer.order.infra.mapper.ItemSnapshotMapper;
import org.skyer.order.infra.util.CollectorsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 内部订单商品明细快照 资源库实现
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@Component
public class ItemSnapshotRepositoryImpl extends BaseRepositoryImpl<ItemSnapshot> implements ItemSnapshotRepository {
    @Autowired
    private ItemSnapshotMapper itemSnapshotMapper;

    @Override
    public Map<String, ItemSnapshot> querySnapshot(String innerOrderNo, List<String> skuCode) {
        List<ItemSnapshot> itemSnapshots = itemSnapshotMapper.selectByCondition(Condition.builder(ItemSnapshot.class)
                .andWhere(Sqls.custom().andEqualTo(ItemSnapshot.FIELD_INNER_ORDER_NO, innerOrderNo).andIn(ItemSnapshot.FIELD_SKU_CODE, skuCode)).build());
        if (CollectionUtils.isEmpty(itemSnapshots)) {
            return null;
        }
        Map<String, ItemSnapshot> skuSnapshotMap = itemSnapshots.stream().collect(Collectors.toMap(ItemSnapshot::getSkuCode, Function.identity(), (o1, o2) -> o1));
        return skuSnapshotMap;
    }

    @Override
    public List<ItemSnapshot> querySnapShotList(String innerOrderNo, List<String> skuCode) {
        List<ItemSnapshot> itemSnapshots = itemSnapshotMapper.selectByCondition(Condition.builder(ItemSnapshot.class)
                .andWhere(Sqls.custom().andEqualTo(ItemSnapshot.FIELD_INNER_ORDER_NO, innerOrderNo).andIn(ItemSnapshot.FIELD_SKU_CODE, skuCode)).build());
        if (CollectionUtils.isEmpty(itemSnapshots)) {
            return null;
        }
        return itemSnapshots;
    }
}
