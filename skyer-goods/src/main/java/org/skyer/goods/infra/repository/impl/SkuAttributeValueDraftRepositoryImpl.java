package org.skyer.goods.infra.repository.impl;

import org.skyer.goods.infra.mapper.SkuAttributeValueDraftMapper;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.goods.domain.entity.SkuAttributeValueDraft;
import org.skyer.goods.domain.repository.SkuAttributeValueDraftRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * sku属性关联草稿表 资源库实现
 *
 * @author zhanglongbing@ebc.com 2021-12-29 11:23:43
 */
@Component
public class SkuAttributeValueDraftRepositoryImpl extends BaseRepositoryImpl<SkuAttributeValueDraft> implements SkuAttributeValueDraftRepository {

    @Autowired
    private SkuAttributeValueDraftMapper skuAttributeValueDraftMapper;
    @Override
    public List<SkuAttributeValueDraft> queryAllBySkuId(Long skuId) {
        return skuAttributeValueDraftMapper.queryAllBySkuId(skuId);
    }
}
