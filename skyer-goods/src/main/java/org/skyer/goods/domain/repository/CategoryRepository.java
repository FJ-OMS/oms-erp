package org.skyer.goods.domain.repository;

import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.CategoryQueryDTO;
import org.skyer.goods.domain.vo.CategoryVO;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.goods.domain.entity.Category;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

import java.util.List;


/**
 * 商品分类表资源库
 *
 * @author xurongfa 2021-12-24 09:12:57
 */
public interface CategoryRepository extends BaseRepository<Category> {

    /**
     * 获取全部分类
     * @return 全部分类
     */
    List<Category> getCategoryAll();

    /**
     * 查询商品分类列表
     * @param categoryQueryDTO 通用查询对象
     * @return 商品分类
     */
    List<Category> selectList(CategoryQueryDTO categoryQueryDTO);

    /**
     * 分页获取分类列表
     * @param category 查询对象
     * @return 带分页的分类列表
     */
    Page<CategoryVO> pageIncludedAttr(PageRequest pageRequest, CategoryQueryDTO category);

    /**
     * 根据分类ID获取分类名层级结构
     * @param categoryId
     * @return String
     */
    String  getCategoryNameById(Long categoryId);

    /**
     * 查询分类子节点Id
     * @param categoryIdList
     * @return
     */
	List<Long> getLaterCategoryId(List<Long> categoryIdList);

    /**
     * 根据父级类目id获取子级类目
     * @param parentId
     * @return
     */
    List<Category> getSonCategory(Long parentId);

    /**
     * 根据某一个类目查询所有后代
     * @param parentId
     * @return
     */
    void getLaterCategory(List<Category> allCategoryRes, Long parentId);
}
