package org.skyer.stock.infra.repository.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.util.Sqls;
import org.skyer.stock.api.dto.AdjustPageReqDTO;
import org.skyer.stock.domain.entity.AdjustRecord;
import org.skyer.stock.domain.repository.AdjustRecordRepository;
import org.skyer.stock.domain.vo.AdjustRecordPageResVO;
import org.skyer.stock.infra.mapper.AdjustRecordMapper;

/**
 * 库存调整单 资源库实现
 *
 * @author lixiaoyang 2021-08-02 16:44:33
 */
@Component
public class AdjustRecordRepositoryImpl extends BaseRepositoryImpl<AdjustRecord> implements AdjustRecordRepository {
    @Autowired
    private AdjustRecordMapper adjustRecordMapper;

    @Override
    public Page<AdjustRecordPageResVO> pageAdjustRecord(AdjustPageReqDTO adjustPageReqDTO, Long tenantId) {
        return PageHelper.doPage(adjustPageReqDTO.getPage(), adjustPageReqDTO.getSize(),
                () -> adjustRecordMapper.listAdjustRecordVO(adjustPageReqDTO, tenantId));
    }

    @Override
    public AdjustRecord getOne(Long id, Long tenantId) {
        List<AdjustRecord> adjustRecords = adjustRecordMapper.selectByCondition(Condition.builder(AdjustRecord.class)
                .andWhere(Sqls.custom().andEqualTo(AdjustRecord.FIELD_ID, id).andEqualTo(AdjustRecord.FIELD_TENANT_ID, tenantId)).build());
        if (CollectionUtils.isEmpty(adjustRecords)) {
            return null;
        }
        return adjustRecords.get(0);
    }

    @Override
    public List<AdjustRecord> getAllByLogicWarehouseIds(List<Long> logicWarehouseIds) {
        if (ObjectUtils.isEmpty(logicWarehouseIds)) {
            return new ArrayList<>();
        }
        return adjustRecordMapper.getAllByLogicWarehouseIds(logicWarehouseIds);
    }
}
