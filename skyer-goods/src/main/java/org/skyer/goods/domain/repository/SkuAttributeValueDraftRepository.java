package org.skyer.goods.domain.repository;

import java.util.List;

import org.skyer.goods.domain.entity.SkuAttributeValue;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.goods.domain.entity.SkuAttributeValueDraft;

/**
 * sku属性关联草稿表资源库
 *
 * @author zhanglongbing@ebc.com 2021-12-29 11:23:43
 */
public interface SkuAttributeValueDraftRepository extends BaseRepository<SkuAttributeValueDraft> {
    /**
     * 根据SKU ID获取关联的属性
     *
     * @author lixiaoyang
     * @date 2021/12/30 11:20
     */
    List<SkuAttributeValueDraft> queryAllBySkuId(Long skuId);
}
