package org.skyer.stock.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.stock.api.dto.WarehouseWarrantPageQueryDTO;
import org.skyer.stock.domain.entity.WarehouseWarrant;
import org.skyer.stock.domain.repository.WarehouseWarrantRepository;
import org.skyer.stock.domain.vo.WarehouseWarrantVO;
import org.skyer.stock.infra.mapper.WarehouseWarrantMapper;

/**
 * 入库单 资源库实现
 *
 * @author lixiaoyang 2021-10-25 16:34:17
 */
@Component
public class WarehouseWarrantRepositoryImpl extends BaseRepositoryImpl<WarehouseWarrant> implements WarehouseWarrantRepository {
    @Autowired
    private WarehouseWarrantMapper warehouseWarrantMapper;

    @Override
    public Page<WarehouseWarrantVO> page(PageRequest pageRequest, WarehouseWarrantPageQueryDTO dto) {
        return PageHelper.doPageAndSort(pageRequest, () -> warehouseWarrantMapper.selectList(dto));
    }

    @Override
    public WarehouseWarrant getOneByFrontRecordTypeAndFrontRecordCode(String frontRecordType, String frontRecordCode) {
        return warehouseWarrantMapper.getOneByFrontRecordTypeAndFrontRecordCode(frontRecordType,frontRecordCode);
    }
}
