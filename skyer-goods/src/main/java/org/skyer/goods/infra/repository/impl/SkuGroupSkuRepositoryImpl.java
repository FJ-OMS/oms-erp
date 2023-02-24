package org.skyer.goods.infra.repository.impl;

import org.skyer.goods.api.dto.GoodsGroupSkuReqDTO;
import org.skyer.goods.infra.constant.ObjectConstant;
import org.skyer.goods.infra.mapper.SkuGroupMapper;
import org.skyer.goods.infra.mapper.SkuGroupSkuMapper;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.goods.domain.entity.SkuGroupSku;
import org.skyer.goods.domain.repository.SkuGroupSkuRepository;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sku组合和sku关联表 资源库实现
 *
 * @author wushaochuan 2021-08-13 09:55:04
 */
@Component
public class SkuGroupSkuRepositoryImpl extends BaseRepositoryImpl<SkuGroupSku> implements SkuGroupSkuRepository {
    @Autowired
    private SkuGroupMapper skuGroupMapper;
    @Autowired
    private SkuGroupSkuMapper skuGroupSkuMapper;
    @Override
    public void createGroupSku(List<GoodsGroupSkuReqDTO> goodsGroupSkuReqDTOS,Long spuId, Long groupId, Long tenantId) {
        List<SkuGroupSku> skuGroupSkus = skuGroupSkuMapper.selectByCondition(Condition.builder(SkuGroupSku.class).andWhere(Sqls.custom()
                .andEqualTo(SkuGroupSku.FIELD_SKU_GROUP_ID, groupId).andEqualTo(SkuGroupSku.FIELD_TENANT_ID, tenantId)
                .andEqualTo(SkuGroupSku.FIELD_DELETE_FLAG, ObjectConstant.DeleteFlag.NO)).build());
        this.batchDeleteByPrimaryKey(skuGroupSkus);
        List<SkuGroupSku> skuGroupSkuList = goodsGroupSkuReqDTOS.stream().map(goodsGroupSkuReqDTO -> {
            SkuGroupSku skuGroupSku = new SkuGroupSku();
            skuGroupSku.setSkuGroupId(groupId);
            skuGroupSku.setSpuId(spuId);
            skuGroupSku.setSkuId(goodsGroupSkuReqDTO.getSkuId());
            skuGroupSku.setNum(goodsGroupSkuReqDTO.getNum());
            skuGroupSku.setTenantId(tenantId);
            return skuGroupSku;
        }).collect(Collectors.toList());
        this.batchInsert(skuGroupSkuList);
    }

    @Override
    public List<SkuGroupSku> queryList(Long groupId) {
        List<SkuGroupSku> skuGroupSkus = skuGroupSkuMapper.selectByCondition(Condition.builder(SkuGroupSku.class).andWhere(Sqls.custom()
                .andEqualTo(SkuGroupSku.FIELD_SKU_GROUP_ID, groupId).andEqualTo(SkuGroupSku.FIELD_DELETE_FLAG, ObjectConstant.DeleteFlag.NO)).build());

        return skuGroupSkus;
    }

    @Override
    public BigDecimal totalSkuNum(Long groupId) {
        BigDecimal skuNum = skuGroupSkuMapper.totalSkuNum(groupId);
        return skuNum;
    }
}

