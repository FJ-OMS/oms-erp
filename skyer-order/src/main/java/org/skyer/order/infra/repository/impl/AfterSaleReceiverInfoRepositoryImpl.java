package org.skyer.order.infra.repository.impl;

import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.domain.entity.AfterSaleReceiverInfo;
import org.skyer.order.domain.repository.AfterSaleReceiverInfoRepository;
import org.skyer.order.infra.mapper.AfterSaleReceiverInfoMapper;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 内部售后收货 资源库实现
 *
 * @author linzhuanghuang@ebc.com 2022-04-26 17:19:33
 */
@Component
public class AfterSaleReceiverInfoRepositoryImpl extends BaseRepositoryImpl<AfterSaleReceiverInfo> implements AfterSaleReceiverInfoRepository {

	@Autowired
	private AfterSaleReceiverInfoMapper afterSaleReceiverInfoMapper;

	@Override
	public AfterSaleReceiverInfo queryByInnerRefundOrderNo(String innerRefundNo) {
		List<AfterSaleReceiverInfo> afterSaleReceiverInfos = afterSaleReceiverInfoMapper.selectByCondition(Condition.builder(AfterSaleReceiverInfo.class)
				.andWhere(Sqls.custom().andEqualTo(AfterSaleReceiverInfo.FIELD_INNER_REFUND_NO, innerRefundNo)).build());
		if (CollUtil.isEmpty(afterSaleReceiverInfos)){
			return null;
		}
		return afterSaleReceiverInfos.get(0);
	}
}
