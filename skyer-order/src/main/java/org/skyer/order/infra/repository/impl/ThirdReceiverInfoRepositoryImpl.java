package org.skyer.order.infra.repository.impl;

import cn.hutool.core.collection.CollUtil;
import java.util.Collections;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.domain.entity.ReceiverInfo;
import org.skyer.order.domain.entity.ThirdReceiverInfo;
import org.skyer.order.domain.repository.ThirdReceiverInfoRepository;
import org.skyer.order.infra.mapper.ThirdReceiverInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 内部订单收货人信息 资源库实现
 *
 * @author zhanglongbing@ebc.com 2022-04-20 10:15:57
 */
@Component
public class ThirdReceiverInfoRepositoryImpl extends BaseRepositoryImpl<ThirdReceiverInfo> implements ThirdReceiverInfoRepository {
    @Autowired
    private ThirdReceiverInfoMapper thirdReceiverInfoMapper;

    @Override
    public Map<String, ThirdReceiverInfo> queryThirdReceiverInfoByOuterNo(List<String> outOrderList) {
        List<ThirdReceiverInfo> receiverInfoList = thirdReceiverInfoMapper.selectByCondition(Condition.builder(ThirdReceiverInfo.class)
                .andWhere(Sqls.custom().andIn(ThirdReceiverInfo.FIELD_OUTER_ORDER_NO, outOrderList)).build());
        if (CollUtil.isEmpty(receiverInfoList)){
            return Collections.emptyMap();
        }
        return receiverInfoList.stream().collect(Collectors.toMap(ThirdReceiverInfo::getOuterOrderNo, Function.identity(), (o1, o2) -> o1));
    }
}
