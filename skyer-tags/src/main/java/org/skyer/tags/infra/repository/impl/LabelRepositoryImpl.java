package org.skyer.tags.infra.repository.impl;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.tags.api.dto.LabelQueryDTO;
import org.skyer.tags.domain.entity.Label;
import org.skyer.tags.domain.repository.LabelRepository;
import org.skyer.tags.domain.vo.LabelVO;
import org.skyer.tags.infra.mapper.LabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  资源库实现
 *
 * @author xurongfa 2022-01-22 11:33:07
 */
@Component
public class LabelRepositoryImpl extends BaseRepositoryImpl<Label> implements LabelRepository {

    @Autowired
    private LabelMapper labelMapper;

    @Override
    public Page<LabelVO> page(PageRequest pageRequest, LabelQueryDTO labelQuery) {

        return PageHelper.doPageAndSort(pageRequest, ()->labelMapper.selectList(labelQuery));

    }
}
