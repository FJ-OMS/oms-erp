package org.skyer.goods.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.goods.domain.entity.CategoryAttributeValue;

/**
 * 商品分类属性值表资源库
 *
 * @author xurongfa 2021-12-24 09:12:57
 */
public interface CategoryAttributeValueRepository extends BaseRepository<CategoryAttributeValue> {
    /**
     * 根据分类id 删除属性值
     * @param categoryId 分类id
     * @return 删除成功的数量
     */
    int deleteByCategoryId(Long categoryId);

}
