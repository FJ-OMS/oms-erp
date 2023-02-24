package org.skyer.order.infra.repository.impl;

import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.domain.entity.AfterSaleLogistics;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.repository.AfterSaleLogisticsRepository;
import org.skyer.order.infra.mapper.AfterSaleLogisticsMapper;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 *  资源库实现
 *
 * @author lzh 2022-03-18 16:26:17
 */
@Component
public class AfterSaleLogisticsRepositoryImpl extends BaseRepositoryImpl<AfterSaleLogistics> implements AfterSaleLogisticsRepository {


	@Autowired
	private AfterSaleLogisticsMapper afterSaleLogisticsMapper;

	@Override
	public List<AfterSaleLogistics> queryByInnerRefundNo(String inner) {
		List<AfterSaleLogistics> logistics = afterSaleLogisticsMapper.selectByCondition(Condition.builder(AfterSaleLogistics.class)
				.andWhere(Sqls.custom().andEqualTo(AfterSaleLogistics.FIELD_INNER_REFUND_NO, inner)).build());
		if (CollUtil.isEmpty(logistics)) {
			return Collections.emptyList();
		}
		return logistics;
	}
}
