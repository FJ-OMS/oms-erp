package org.skyer.goods.infra.mapper;

import org.skyer.goods.api.dto.CategoryQueryDTO;
import org.skyer.goods.domain.entity.Category;
import org.skyer.goods.domain.vo.CategoryVO;
import org.skyer.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 商品分类表Mapper
 *
 * @author xurongfa 2021-12-24 09:12:57
 */
public interface CategoryMapper extends BaseMapper<Category> {

    List<Category> selectList(CategoryQueryDTO categoryQueryDTO);

    List<CategoryVO> listIncludedAttr(CategoryQueryDTO category);


}
