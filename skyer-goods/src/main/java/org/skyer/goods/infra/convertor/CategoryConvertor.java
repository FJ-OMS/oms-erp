package org.skyer.goods.infra.convertor;

import org.mapstruct.Mapper;
import org.skyer.goods.api.dto.CategoryQueryDTO;
import org.skyer.goods.api.dto.CategorySaveDTO;
import org.skyer.goods.api.dto.CategoryTreeQueryDTO;
import org.skyer.goods.domain.entity.Category;

@Mapper(componentModel="spring")
public interface CategoryConvertor {

	/**
	 * 保存分类对象转换为实体对象
	 * @param categorySaveDTO 保存对象，包含数据
	 * @return 实体对象
	 */
	Category CategorySaveDTOTOCategory(CategorySaveDTO categorySaveDTO);

	/**
	 * 树查询对象转为通用查询对象
	 * @param categoryTreeQueryDTO 查询树的对象
	 * @return 通用查询对象
	 */
	CategoryQueryDTO CategoryTreeQueryDTOToCategoryQueryDTO(CategoryTreeQueryDTO categoryTreeQueryDTO);
}
