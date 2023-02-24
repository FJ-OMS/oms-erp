package org.skyer.goods.infra.repository.impl;

import org.skyer.goods.infra.mapper.SkuAttributeValueMapper;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.goods.domain.entity.SkuAttributeValue;
import org.skyer.goods.domain.repository.SkuAttributeValueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * sku属性关联表 资源库实现
 *
 * @author zhanglongbing@ebc.com 2021-12-28 09:17:10
 */
@Component
public class SkuAttributeValueRepositoryImpl extends BaseRepositoryImpl<SkuAttributeValue> implements SkuAttributeValueRepository {

    @Autowired
    private SkuAttributeValueMapper skuAttributeValueMapper;

    @Override
    public List<SkuAttributeValue> queryAllBySkuId(Long skuId) {
        return skuAttributeValueMapper.queryAllBySkuId(skuId);
    }

    @Override
    public Integer deleteBySkuId(Long skuId) {
        return skuAttributeValueMapper.deleteBySkuId(skuId);
    }
}
