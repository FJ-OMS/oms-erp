package org.skyer.tags.app.service;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.tags.api.dto.LabelListQueryDTO;
import org.skyer.tags.api.dto.LabelQueryDTO;
import org.skyer.tags.api.dto.LabelSaveDTO;
import org.skyer.tags.domain.entity.Label;
import org.skyer.tags.domain.vo.LabelVO;

import java.util.List;

/**
 * 应用服务
 *
 * @author xurongfa 2022-01-22 11:33:07
 */
public interface LabelService {

    /**
     * 分页查询
     * @param pageRequest 分页对象
     * @param labelQuery 查询对象
     * @return 带分页的标签列表
     */
    Page<LabelVO> page(PageRequest pageRequest, LabelQueryDTO labelQuery);


    /***
     *  条件查询标签列表(不分页)
     * @param queryDTO queryDTO
     * @return List<LabelVO>
     */
    List<LabelVO> labelList(LabelListQueryDTO queryDTO);

    /**
     * 批量修改状态
     * @param ids 标签id集合
     * @param status 状态
     */
    void batchUpdateByStatus(List<Long> ids, Boolean status);

    /**
     * 批量删除
     * @param ids 标签id集合
     */
    void batchRemoveByIds(List<Long> ids);

    /**
     * 新增或修改 标签
     * @param label 标签对象
     * @return 修改之后的标签对象
     */
    Label save(LabelSaveDTO label);
}
