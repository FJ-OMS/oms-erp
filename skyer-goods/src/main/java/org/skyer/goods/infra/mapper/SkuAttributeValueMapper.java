package org.skyer.goods.infra.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.skyer.goods.domain.entity.SkuAttributeValue;
import org.skyer.mybatis.common.BaseMapper;

/**
 * sku属性关联表Mapper
 *
 * @author zhanglongbing@ebc.com 2021-12-28 09:17:10
 */
public interface SkuAttributeValueMapper extends BaseMapper<SkuAttributeValue> {
    /**
     * 根据SKU ID获取关联的属性
     *
     * @author lixiaoyang
     * @date 2021/12/30 11:21
     */
    List<SkuAttributeValue> queryAllBySkuId(@Param("skuId") Long skuId);

    /**
     * 根据SKU ID批量删除
     *
     * @author lixiaoyang
     * @date 2021/12/30 15:22
     */
    Integer deleteBySkuId(@Param("skuId") Long skuId);

}
