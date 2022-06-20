package org.skyer.tags.infra.mapper;

import org.skyer.tags.api.dto.LabelQueryDTO;
import org.skyer.tags.domain.entity.Label;
import org.skyer.mybatis.common.BaseMapper;
import org.skyer.tags.domain.vo.LabelVO;

import java.util.List;

/**
 * Mapper
 *
 * @author xurongfa 2022-01-22 11:33:07
 */
public interface LabelMapper extends BaseMapper<Label> {
    /**
     * 查询列表
     * @param labelQuery 查询条件
     * @return {@link LabelVO}
     */
    List<LabelVO> selectList(LabelQueryDTO labelQuery);
}
