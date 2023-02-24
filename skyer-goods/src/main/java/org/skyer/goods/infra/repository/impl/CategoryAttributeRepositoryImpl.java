package org.skyer.goods.infra.repository.impl;

import org.skyer.goods.api.dto.CategoryAttributeQueryDTO;
import org.skyer.goods.domain.entity.CategoryAttribute;
import org.skyer.goods.domain.repository.CategoryAttributeRepository;
import org.skyer.goods.domain.vo.CategoryAttributeVO;
import org.skyer.goods.infra.mapper.CategoryAttributeMapper;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商品分类属性表 资源库实现
 *
 * @author xurongfa 2021-12-24 09:12:57
 */
@Component
public class CategoryAttributeRepositoryImpl extends BaseRepositoryImpl<CategoryAttribute> implements CategoryAttributeRepository {

    @Autowired
    private CategoryAttributeMapper categoryAttributeMapper;

    public List<CategoryAttributeVO> listIncludedValue(CategoryAttributeQueryDTO param){
        return categoryAttributeMapper.listIncludedValue(param);
    }

}
