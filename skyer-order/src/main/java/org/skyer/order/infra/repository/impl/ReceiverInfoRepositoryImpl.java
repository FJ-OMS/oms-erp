package org.skyer.order.infra.repository.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.domain.entity.ReceiverInfo;
import org.skyer.order.domain.repository.ReceiverInfoRepository;
import org.skyer.order.infra.mapper.ReceiverInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 内部订单收货人信息 资源库实现
 *
 * @author wushaochuan 2022-01-18 14:34:53
 */
@Component
public class ReceiverInfoRepositoryImpl extends BaseRepositoryImpl<ReceiverInfo> implements ReceiverInfoRepository {
    @Autowired
    private ReceiverInfoMapper receiverInfoMapper;

    @Override
    public Map<String,ReceiverInfo> queryReceiveInfoListByInner(List<String> innerNoList) {
        List<ReceiverInfo> receiverInfoList = receiverInfoMapper.selectByCondition(Condition.builder(ReceiverInfo.class).andWhere(Sqls.custom().andIn(ReceiverInfo.FIELD_INNER_ORDER_NO, innerNoList)).build());
        if (CollUtil.isEmpty(receiverInfoList)){
            return null;
        }
        return receiverInfoList.stream().collect(Collectors.toMap(ReceiverInfo::getInnerOrderNo, Function.identity(), (o1, o2) -> o1));
    }

    @Override
    public ReceiverInfo queryReceiveInfoByInnerNo(String innerNo) {
        List<ReceiverInfo> receiverInfoList = receiverInfoMapper.selectByCondition(Condition.builder(ReceiverInfo.class).andWhere(Sqls.custom().andEqualTo(ReceiverInfo.FIELD_INNER_ORDER_NO, innerNo)).build());
        if (CollUtil.isEmpty(receiverInfoList)) {
            return null;
        }
        return receiverInfoList.get(0);

    }
}
