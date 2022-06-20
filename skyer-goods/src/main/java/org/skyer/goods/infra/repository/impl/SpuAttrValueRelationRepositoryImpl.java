package org.skyer.goods.infra.repository.impl;

import cn.hutool.core.collection.CollectionUtil;

import org.skyer.goods.domain.entity.SpuAttrValueRelation;
import org.skyer.goods.infra.constant.ObjectConstant;
import org.skyer.goods.infra.mapper.SpuAttrValueRelationMapper;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.goods.domain.repository.SpuAttrValueRelationRepository;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品和属性值关联关系 资源库实现
 *
 * @author wushaochuan 2021-07-19 09:18:33
 */
@Component
public class SpuAttrValueRelationRepositoryImpl extends BaseRepositoryImpl<SpuAttrValueRelation> implements SpuAttrValueRelationRepository {
    @Autowired
    private SpuAttrValueRelationMapper spuAttrValueRelationMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createOrUpdateGoodsAttrRelation(Long goodsId,List<SpuAttrValueRelation> spuAttrValueRelationList) {
        //先删除对应商品id的记录
        List<SpuAttrValueRelation> spuAttrValueRelations = queryGoodsAttribute(goodsId);
        if (CollectionUtil.isNotEmpty(spuAttrValueRelations)) {
            this.deleteSpuAttrValueRelation(goodsId,null);
        }
        //更新对应记录
        this.batchInsertSelective(spuAttrValueRelationList);
    }

    @Override
    public List<SpuAttrValueRelation> queryGoodsAttribute(Long goodsId) {
        return spuAttrValueRelationMapper.selectByCondition(Condition.builder(SpuAttrValueRelation.class).andWhere(Sqls.custom().andEqualTo(SpuAttrValueRelation.FIELD_GOODS_ID, goodsId)
                .andEqualTo(SpuAttrValueRelation.FIELD_DELETE_FLAG, ObjectConstant.DeleteFlag.NO)).build());
    }

    @Override
    public void deleteSpuAttrValueRelation(Long spuId, Long valueId) {
        Sqls sqls = Sqls.custom().andEqualTo(SpuAttrValueRelation.FIELD_GOODS_ID, spuId).andEqualTo(SpuAttrValueRelation.FIELD_DELETE_FLAG, ObjectConstant.DeleteFlag.NO);
        if (valueId != null){
            sqls.andEqualTo(SpuAttrValueRelation.FIELD_ATTRIBUTE_VALUE_ID,valueId);
        }
        List<SpuAttrValueRelation> spuAttrValueRelationList = spuAttrValueRelationMapper.selectByCondition(Condition.builder(SpuAttrValueRelation.class).andWhere(sqls).build());
        spuAttrValueRelationList.forEach(x->{
            x.setDeleteFlag(ObjectConstant.DeleteFlag.YES);
        });
        this.batchUpdateByPrimaryKeySelective(spuAttrValueRelationList);
    }
}
