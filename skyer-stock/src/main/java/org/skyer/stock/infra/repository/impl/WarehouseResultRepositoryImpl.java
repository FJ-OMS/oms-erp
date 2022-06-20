package org.skyer.stock.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.stock.api.dto.WarehouseResultPageQueryDTO;
import org.skyer.stock.domain.entity.WarehouseResult;
import org.skyer.stock.domain.repository.WarehouseResultRepository;
import org.skyer.stock.domain.vo.WarehouseResultVO;
import org.skyer.stock.infra.mapper.WarehouseResultMapper;

/**
 * 入库结果单 资源库实现
 *
 * @author xurongfa 2022-04-26 14:10:52
 */
@Component
public class WarehouseResultRepositoryImpl extends BaseRepositoryImpl<WarehouseResult> implements WarehouseResultRepository {

    @Autowired
    private WarehouseResultMapper warehouseResultMapper;

    @Override
    public Page<WarehouseResultVO> page(PageRequest pageRequest, WarehouseResultPageQueryDTO dto) {
        return PageHelper.doPageAndSort(pageRequest, ()->warehouseResultMapper.selectList(dto));
    }
}
