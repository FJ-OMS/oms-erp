package org.skyer.stock.infra.repository.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.util.Sqls;
import org.skyer.stock.api.dto.LogisticsResultDTO;
import org.skyer.stock.api.dto.ResultRecordPageDTO;
import org.skyer.stock.domain.entity.ResultRecord;
import org.skyer.stock.domain.repository.ResultRecordRepository;
import org.skyer.stock.domain.vo.LogisticsResultVO;
import org.skyer.stock.domain.vo.ResultRecordListVO;
import org.skyer.stock.infra.mapper.ResultRecordMapper;

/**
 * 收发货结果单 资源库实现
 *
 * @author lixiaoyang 2021-08-03 16:41:29
 */
@Component
public class ResultRecordRepositoryImpl extends BaseRepositoryImpl<ResultRecord> implements ResultRecordRepository {

    @Autowired
    private ResultRecordMapper resultRecordMapper;

    @Override
    public ResultRecord getResultByCode(String resultCode) {
        List<ResultRecord> resultRecords = resultRecordMapper.selectByCondition(Condition.builder(ResultRecord.class).andWhere(Sqls.custom()
                .andEqualTo(ResultRecord.FIELD_RESULT_CODE, resultCode)).build());
        if (CollectionUtils.isNotEmpty(resultRecords)) {
            return resultRecords.get(0);
        }
        return null;
    }

    @Override
    public Page<ResultRecordListVO> page(PageRequest pageRequest, ResultRecordPageDTO dto) {
        return PageHelper.doPageAndSort(pageRequest, () -> resultRecordMapper.selectList(dto));
    }

    @Override
    public List<LogisticsResultVO> statsLogisticsResult(LogisticsResultDTO dto) {
        return resultRecordMapper.statsLogisticsResult(dto);
    }
}
