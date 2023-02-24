package org.skyer.goods.infra.mapper;

import org.skyer.goods.domain.entity.CategoryAttributeValue;
import org.skyer.mybatis.common.BaseMapper;

/**
 * 商品分类属性值表Mapper
 *
 * @author xurongfa 2021-12-24 09:12:57
 */
public interface CategoryAttributeValueMapper extends BaseMapper<CategoryAttributeValue> {

    /**
     * 根据分类id 删除属性值
     * @param categoryId 分类id
     * @return 删除成功的数量
     */
    int deleteByCategoryId(Long categoryId);
}
