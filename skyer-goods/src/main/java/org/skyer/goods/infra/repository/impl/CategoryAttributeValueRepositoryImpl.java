package org.skyer.goods.infra.repository.impl;

import org.skyer.goods.infra.mapper.CategoryAttributeValueMapper;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.goods.domain.entity.CategoryAttributeValue;
import org.skyer.goods.domain.repository.CategoryAttributeValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商品分类属性值表 资源库实现
 *
 * @author xurongfa 2021-12-24 09:12:57
 */
@Component
public class CategoryAttributeValueRepositoryImpl extends BaseRepositoryImpl<CategoryAttributeValue> implements CategoryAttributeValueRepository {

    @Autowired
    private CategoryAttributeValueMapper categoryAttributeValueMapper;

    /**
     * 根据分类id 删除属性值
     * @param categoryId 分类id
     * @return 删除成功的数量
     */
    @Override
    public int deleteByCategoryId(Long categoryId){
        return categoryAttributeValueMapper.deleteByCategoryId(categoryId);
    }
  
}
