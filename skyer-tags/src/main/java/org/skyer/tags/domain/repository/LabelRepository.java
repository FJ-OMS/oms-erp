package org.skyer.tags.domain.repository;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.tags.api.dto.LabelQueryDTO;
import org.skyer.tags.domain.entity.Label;
import org.skyer.tags.domain.vo.LabelVO;

/**
 * 资源库
 *
 * @author xurongfa 2022-01-22 11:33:07
 */
public interface LabelRepository extends BaseRepository<Label> {
    /**
     * 分页
     * @param pageRequest 分页条件
     * @param labelQuery 查询条件
     * @return {@link LabelVO}
     */
    Page<LabelVO> page(PageRequest pageRequest, LabelQueryDTO labelQuery);
    
}
