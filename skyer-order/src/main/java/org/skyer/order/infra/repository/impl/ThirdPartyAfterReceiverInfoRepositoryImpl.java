package org.skyer.order.infra.repository.impl;

import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.domain.entity.ThirdPartyAfterReceiverInfo;
import org.skyer.order.domain.entity.ThirdPartyAfterSaleItem;
import org.skyer.order.domain.repository.ThirdPartyAfterReceiverInfoRepository;
import org.skyer.order.infra.mapper.ThirdPartyAfterReceiverInfoMapper;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 第三方售后收货人 资源库实现
 *
 * @author lzh 2022-04-20 16:30:30
 */
@Component
public class ThirdPartyAfterReceiverInfoRepositoryImpl extends BaseRepositoryImpl<ThirdPartyAfterReceiverInfo> implements ThirdPartyAfterReceiverInfoRepository {

	@Autowired
	private ThirdPartyAfterReceiverInfoMapper thirdPartyAfterReceiverInfoMapper;

	@Override
	public ThirdPartyAfterReceiverInfo queryByOuterRefundNo(String outerRefundNo) {
		List<ThirdPartyAfterReceiverInfo> infos = thirdPartyAfterReceiverInfoMapper.selectByCondition(Condition.builder(ThirdPartyAfterReceiverInfo.class).andWhere(Sqls.custom().andEqualTo(ThirdPartyAfterReceiverInfo.FIELD_OUT_REFUND_NO, outerRefundNo)).build());
		if (CollUtil.isEmpty(infos)) {
			return null;
		}
		return infos.get(0);
	}
}
