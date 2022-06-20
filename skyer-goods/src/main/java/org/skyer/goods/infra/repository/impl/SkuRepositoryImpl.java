package org.skyer.goods.infra.repository.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.GoodsSkuPageDTO;
import org.skyer.goods.domain.entity.Sku;
import org.skyer.goods.domain.repository.SkuRepository;
import org.skyer.goods.domain.vo.RpcGoodsResVO;
import org.skyer.goods.domain.vo.RpcOrderResVO;
import org.skyer.goods.infra.mapper.SkuMapper;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.util.Sqls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品规格表 资源库实现
 *
 * @author zhanglongbing@ebc.com 2021-12-27 14:48:20
 */
@Component
public class SkuRepositoryImpl extends BaseRepositoryImpl<Sku> implements SkuRepository {
    @Autowired
    private SkuMapper skuMapper;

    @Override
    public Page<Sku> pageSku(GoodsSkuPageDTO skuPageDTO) {
        return PageHelper.doPage(skuPageDTO.getPage(), skuPageDTO.getSize(), () -> skuMapper.getSkuList(skuPageDTO));
    }

    @Override
    public Sku checkHaveSameSku(Long spuId,String md5Long) {
        List<Sku> skuList = skuMapper.selectByCondition(Condition.builder(Sku.class)
                .andWhere(Sqls.custom().andEqualTo(Sku.FIELD_MD5_CHECK_VALUE, md5Long).andEqualTo(Sku.FIELD_SPU_ID,spuId,true)).build());
        if (CollUtil.isNotEmpty(skuList)) {
            return skuList.get(0);
        }
        return null;
    }

    @Override
    public Sku checkHaveSameSkuCode(String skuCode) {
        List<Sku> skuList = skuMapper.selectByCondition(Condition.builder(Sku.class).andWhere(Sqls.custom().andEqualTo(Sku.FIELD_SKU_CODE, skuCode)).build());
        if (CollUtil.isNotEmpty(skuList)) {
            return skuList.get(0);
        }
        return null;
    }

    @Override
    public Sku checkHaveSameSkuCodeWithOutSkuId(String skuCode, Long skuId) {
        List<Sku> skuList = skuMapper.selectByCondition(Condition.builder(Sku.class)
                .andWhere(Sqls.custom().andEqualTo(Sku.FIELD_SKU_CODE, skuCode).andNotEqualTo(Sku.FIELD_ID, skuId)).build());
        if (CollUtil.isNotEmpty(skuList)) {
            return skuList.get(0);
        }
        return null;
    }

    @Override
    public List<Sku> getSkuListBySpuId(Long tenantId, Long spuId) {
        return skuMapper.selectByCondition(Condition.builder(Sku.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(Sku.FIELD_SPU_ID, spuId, true))
                .build());
    }

    @Override
    public List<Sku> getSkuListBySkuCode(List<String> skuCodeList) {
        return skuMapper.selectByCondition(Condition.builder(Sku.class)
                .andWhere(Sqls.custom()
                        .andIn(Sku.FIELD_SKU_CODE, skuCodeList, true))
                .build());
    }

    @Override
    public List<Sku> getSkuListBySkuId(List<Long> skuIdList) {
        return skuMapper.selectByCondition(Condition.builder(Sku.class)
                .andWhere(Sqls.custom()
                        .andIn(Sku.FIELD_ID, skuIdList, true))
                .build());
    }

    @Override
    public Boolean getAttributeValueAndSpu(String attributeValue, Long spuId, Long skuId) {
        List<Sku> skuList = skuMapper.selectByCondition(Condition.builder(Sku.class).andWhere(Sqls.custom().andEqualTo(Sku.FIELD_SPU_ID, spuId).andLike(Sku.FIELD_ATTRIBUTE_VALUE, attributeValue)
                .andNotEqualTo(Sku.FIELD_ID, skuId)).build());
        if (CollUtil.isEmpty(skuList)) {
            return true;
        }
        return false;
    }

    @Override
    public Sku getSkuByCode(String code) {
        List<Sku> skuList = skuMapper.selectByCondition(Condition.builder(Sku.class).andWhere(Sqls.custom().andEqualTo(Sku.FIELD_SKU_CODE, code)).build());
        if (CollUtil.isEmpty(skuList)) {
            return null;
        }
        return skuList.get(0);
    }

    @Override
    public List<RpcGoodsResVO> queryRPCGoodsList(List<String> skuCodeList) {
        List<RpcGoodsResVO> rpcGoodsResVOS = skuMapper.queryRPCGoodsList(skuCodeList);
        return rpcGoodsResVOS;
    }

    @Override
    public List<RpcOrderResVO> queryOrderRpcList(List<String> skuCodeList) {
        List<RpcOrderResVO> rpcGoodsResVos = skuMapper.queryOrderRpcList(skuCodeList);
        return rpcGoodsResVos;
    }

}
