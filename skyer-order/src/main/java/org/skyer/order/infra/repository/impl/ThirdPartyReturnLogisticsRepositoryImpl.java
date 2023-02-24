package org.skyer.order.infra.repository.impl;

import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.domain.entity.ThirdPartyAfterReceiverInfo;
import org.skyer.order.domain.entity.ThirdPartyAfterSaleItem;
import org.skyer.order.domain.entity.ThirdPartyReturnLogistics;
import org.skyer.order.domain.repository.ThirdPartyReturnLogisticsRepository;
import org.skyer.order.infra.mapper.ThirdPartyReturnLogisticsMapper;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 三方退货物流信息 资源库实现
 *
 * @author lzh 2022-04-20 16:30:29
 */
@Component
public class ThirdPartyReturnLogisticsRepositoryImpl extends BaseRepositoryImpl<ThirdPartyReturnLogistics> implements ThirdPartyReturnLogisticsRepository {


	@Autowired
	private ThirdPartyReturnLogisticsMapper thirdPartyReturnLogisticsMapper;

	@Override
	public ThirdPartyReturnLogistics queryByOuterRefundNo(String outerRefundNo) {
		List<ThirdPartyReturnLogistics> vos = thirdPartyReturnLogisticsMapper.selectByCondition(Condition.builder(ThirdPartyReturnLogistics.class).andWhere(Sqls.custom().andEqualTo(ThirdPartyReturnLogistics.FIELD_OUTER_REFUND_NO, outerRefundNo)).build());
		if (CollUtil.isEmpty(vos)) {
			return null;
		}
		return vos.get(0);
	}
}
