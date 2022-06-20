package org.skyer.stock.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.stock.api.dto.StockChangeListDTO;
import org.skyer.stock.domain.entity.ChangeLog;
import org.skyer.stock.domain.repository.ChangeLogRepository;
import org.skyer.stock.domain.vo.StockChangeListVO;
import org.skyer.stock.infra.mapper.ChangeLogMapper;

/**
 * 库存流水表 资源库实现
 *
 * @author lixiaoyang 2021-08-03 16:51:06
 */
@Component
public class ChangeLogRepositoryImpl extends BaseRepositoryImpl<ChangeLog> implements ChangeLogRepository {
    @Autowired
    private ChangeLogMapper changeLogMapper;

    @Override
    public Page<StockChangeListVO> getStockChangePage(StockChangeListDTO dto, Long tenantId, PageRequest pageRequest) {
        return PageHelper.doPageAndSort(pageRequest, () -> changeLogMapper.getStockChangeList(dto, tenantId));
    }

    @Override
    public BigDecimal getPutNum(StockChangeListDTO dto, Long tenantId) {
        return changeLogMapper.getNum(dto,tenantId,true);
    }

    @Override
    public BigDecimal getOutNum(StockChangeListDTO dto, Long tenantId) {
        return changeLogMapper.getNum(dto,tenantId,false);
    }
}
