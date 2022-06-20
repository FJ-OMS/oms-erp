package org.skyer.goods.domain.service;

import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.CategoryQueryDTO;
import org.skyer.goods.api.dto.CategorySaveDTO;
import org.skyer.goods.api.dto.CategoryTreeQueryDTO;
import org.skyer.goods.api.dto.CategoryUpdateByStatusFlagDTO;
import org.skyer.goods.domain.vo.CategoryTreeVO;
import org.skyer.goods.domain.vo.CategoryVO;

import java.util.List;

public interface CategoryManageService {
    /**
     * 更新或添加分类，包括 属性和属性值
     * @param category 分类信息
     */
    void save(CategorySaveDTO category);
    /**
     * 修改商品分类状态
     */
    void updateByStatusFlag(CategoryUpdateByStatusFlagDTO categoryUpdateByStatusFlagDTO);
    /**
     * 批量删除分类
     * @param ids 删除的分类id集合
     */
    void batchDelete(List<Long> ids);

    /**
     * 获取分类树
     * @return 树型分类
     */
    List<CategoryTreeVO> getCategoryTree(CategoryTreeQueryDTO category);

    /**
     * 查询单级分类，包含属性
     * @param categoryQueryDTO 默认父id为0
     * @return 分页数据
     */
    Page<CategoryVO> pageIncludedAttr(CategoryQueryDTO categoryQueryDTO);
}
