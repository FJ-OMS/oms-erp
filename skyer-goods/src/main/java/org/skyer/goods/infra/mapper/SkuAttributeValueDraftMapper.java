package org.skyer.goods.infra.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.skyer.goods.domain.entity.SkuAttributeValueDraft;
import org.skyer.mybatis.common.BaseMapper;

/**
 * sku属性关联草稿表Mapper
 *
 * @author zhanglongbing@ebc.com 2021-12-29 11:23:43
 */
public interface SkuAttributeValueDraftMapper extends BaseMapper<SkuAttributeValueDraft> {

    /**
     * 根据SKU ID获取关联的属性
     *
     * @author lixiaoyang
     * @date 2021/12/30 11:20
     */
    List<SkuAttributeValueDraft> queryAllBySkuId(@Param("skuId") Long skuId);
}
