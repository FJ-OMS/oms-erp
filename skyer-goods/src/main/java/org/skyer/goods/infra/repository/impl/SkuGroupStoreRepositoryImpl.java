package org.skyer.goods.infra.repository.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.skyer.goods.domain.entity.SkuGroup;
import org.skyer.goods.infra.constant.ObjectConstant;
import org.skyer.goods.infra.mapper.SkuGroupMapper;
import org.skyer.goods.infra.mapper.SkuGroupStoreMapper;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.goods.domain.entity.SkuGroupStore;
import org.skyer.goods.domain.repository.SkuGroupStoreRepository;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Sku组合和店铺表 资源库实现
 *
 * @author wushaochuan 2021-08-13 09:55:04
 */
@Component
public class SkuGroupStoreRepositoryImpl extends BaseRepositoryImpl<SkuGroupStore> implements SkuGroupStoreRepository {
    @Autowired
    private SkuGroupStoreMapper skuGroupStoreMapper;

    @Override
    public void updateSkuGroupStore(List<Long> storeIdList, Long groupId,Long tenantId) {
        List<SkuGroupStore> skuGroups = skuGroupStoreMapper.selectByCondition(Condition.builder(SkuGroupStore.class).andWhere(Sqls.custom()
                .andEqualTo(SkuGroupStore.FIELD_SKU_GROUP_ID, groupId)).build());
        if (CollectionUtils.isNotEmpty(skuGroups)){
            this.batchDeleteByPrimaryKey(skuGroups);
        }
        //删除后添加
        List<SkuGroupStore> skuGroupStoreList = storeIdList.stream().map(storeId -> {
            SkuGroupStore skuGroupStore = new SkuGroupStore();
            skuGroupStore.setStoreId(storeId);
            skuGroupStore.setSkuGroupId(groupId);
            skuGroupStore.setTenantId(tenantId);
            return skuGroupStore;
        }).collect(Collectors.toList());
        this.batchInsertSelective(skuGroupStoreList);
    }

    @Override
    public List<SkuGroupStore> querySkuGroupStoreList(Long groupId) {
        List<SkuGroupStore> skuGroupStores = skuGroupStoreMapper.selectByCondition(Condition.builder(SkuGroupStore.class).andWhere(Sqls.custom()
                .andEqualTo(SkuGroupStore.FIELD_SKU_GROUP_ID, groupId).andEqualTo(SkuGroupStore.FIELD_DELETE_FLAG, ObjectConstant.DeleteFlag.NO)).build());
        return skuGroupStores;
    }
}
