package org.skyer.stock.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.stock.domain.entity.ResultDetail;
import org.skyer.stock.domain.repository.ResultDetailRepository;
import org.skyer.stock.domain.vo.ResultRecordQtyVO;
import org.skyer.stock.infra.mapper.ResultDetailMapper;

/**
 * 收发货结果单详情 资源库实现
 *
 * @author lixiaoyang 2021-08-03 16:41:29
 */
@Component
public class ResultDetailRepositoryImpl extends BaseRepositoryImpl<ResultDetail> implements ResultDetailRepository {

    @Autowired
    private ResultDetailMapper resultDetailMapper;

    @Override
    public List<ResultDetail> queryResultDetail(Long resultId) {
        return resultDetailMapper.selectByCondition(Condition.builder(ResultDetail.class).andWhere(Sqls.custom()
                .andEqualTo(ResultDetail.FIELD_RESULT_ID, resultId)).build());
    }

    @Override
    public ResultRecordQtyVO statsResultRecordQty(Long resultId) {

        return resultDetailMapper.statsByResultId(resultId);
    }
}
