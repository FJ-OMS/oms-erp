package org.skyer.goods.domain.repository;

import org.skyer.goods.api.dto.CategoryAttributeQueryDTO;
import org.skyer.goods.domain.entity.CategoryAttribute;
import org.skyer.goods.domain.vo.CategoryAttributeVO;
import org.skyer.mybatis.base.BaseRepository;

import java.util.List;

/**
 * 商品分类属性表资源库
 *
 * @author xurongfa 2021-12-24 09:12:57
 */
public interface CategoryAttributeRepository extends BaseRepository<CategoryAttribute> {

    /**
     * 查询商品分类属性，含属性值
     * @param param 属性及属性值查询对象
     * @return 带属性值的属性列表
     */
    List<CategoryAttributeVO> listIncludedValue(CategoryAttributeQueryDTO param);

}
