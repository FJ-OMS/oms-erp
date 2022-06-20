package org.skyer.goods.infra.mapper;

import org.skyer.goods.api.dto.CategoryAttributeQueryDTO;
import org.skyer.goods.domain.entity.CategoryAttribute;
import org.skyer.goods.domain.vo.CategoryAttributeVO;
import org.skyer.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 商品分类属性表Mapper
 *
 * @author xurongfa 2021-12-24 09:12:57
 */
public interface CategoryAttributeMapper extends BaseMapper<CategoryAttribute> {

    List<CategoryAttributeVO> listIncludedValue(CategoryAttributeQueryDTO param);

}
