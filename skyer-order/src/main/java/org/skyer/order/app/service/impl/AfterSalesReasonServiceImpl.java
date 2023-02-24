package org.skyer.order.app.service.impl;

import cn.hutool.core.util.ObjectUtil;
import org.skyer.core.exception.CommonException;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.app.service.AfterSalesReasonService;
import org.skyer.order.domain.entity.AfterSalesReason;
import org.skyer.order.domain.repository.AfterSalesReasonRepository;
import org.skyer.order.infra.mapper.AfterSalesReasonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 售后原因应用服务默认实现
 *
 * @author zhanglongbing@ebc.com 2022-03-14 10:41:33
 */
@Service
public class AfterSalesReasonServiceImpl implements AfterSalesReasonService {
    @Autowired
    private AfterSalesReasonRepository afterSalesReasonRepository;

    @Override
    public AfterSalesReason createAfterSalesReason(AfterSalesReason afterSalesReason) {
        List<AfterSalesReason> salesReason = afterSalesReasonRepository.selectByCondition(Condition.builder(AfterSalesReason.class)
                .andWhere(Sqls.custom().andEqualTo(AfterSalesReason.FIELD_REASON_DESCRIBE,afterSalesReason.getReasonDescribe().trim())
                        .andEqualTo(AfterSalesReason.FIELD_DELETE_FLAG,0).andEqualTo(AfterSalesReason.FIELD_TYPE,afterSalesReason.getType())).build());
        if (ObjectUtil.isNotEmpty(salesReason)){
            throw new CommonException("该原因已存在，请重新输入");
        }
        afterSalesReason.setReasonCode("RE"+System.currentTimeMillis());
        afterSalesReasonRepository.insertSelective(afterSalesReason);
        return afterSalesReason;
    }

    @Override
    public AfterSalesReason updateAfterSalesReason(AfterSalesReason afterSalesReason) {
        if (ObjectUtil.isNotNull(afterSalesReason.getStatus())){
            afterSalesReasonRepository.updateOptional(afterSalesReason,AfterSalesReason.FIELD_STATUS);
        }
        if (ObjectUtil.isNotNull(afterSalesReason.getReasonDescribe())){
            List<AfterSalesReason> salesReason = afterSalesReasonRepository.selectByCondition(Condition.builder(AfterSalesReason.class)
                    .andWhere(Sqls.custom().andEqualTo(AfterSalesReason.FIELD_REASON_DESCRIBE,afterSalesReason.getReasonDescribe().trim())
                            .andEqualTo(AfterSalesReason.FIELD_DELETE_FLAG,0)).build());
            if (ObjectUtil.isNotEmpty(salesReason)&&(salesReason.stream().anyMatch(f -> !f.getId().equals(afterSalesReason.getId())))){
                throw new CommonException("该原因已存在，请重新输入");
            }
            afterSalesReasonRepository.updateOptional(afterSalesReason,AfterSalesReason.FIELD_REASON_DESCRIBE);
        }
        return afterSalesReason;
    }




}
