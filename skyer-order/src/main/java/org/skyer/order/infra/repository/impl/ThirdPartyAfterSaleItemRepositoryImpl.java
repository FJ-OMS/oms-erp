package org.skyer.order.infra.repository.impl;

import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.domain.entity.Price;
import org.skyer.order.domain.entity.ThirdPartyAfterSaleItem;
import org.skyer.order.domain.repository.ThirdPartyAfterSaleItemRepository;
import org.skyer.order.infra.mapper.ThirdPartyAfterSaleItemMapper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import kotlin.collections.EmptyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 第三方售后单商品 资源库实现
 *
 * @author lzh 2022-04-20 16:30:29
 */
@Component
public class ThirdPartyAfterSaleItemRepositoryImpl extends BaseRepositoryImpl<ThirdPartyAfterSaleItem> implements ThirdPartyAfterSaleItemRepository {

	@Autowired
	private ThirdPartyAfterSaleItemMapper thirdPartyAfterSaleItemMapper;

	@Override
	public List<ThirdPartyAfterSaleItem> queryByOutRefundNo(String outRefundNo) {
		List<ThirdPartyAfterSaleItem> items = thirdPartyAfterSaleItemMapper.selectByCondition(Condition.builder(ThirdPartyAfterSaleItem.class).andWhere(Sqls.custom().andEqualTo(ThirdPartyAfterSaleItem.FIELD_OUT_REFUND_NO, outRefundNo)).build());
		if (CollUtil.isEmpty(items)) {
			return Collections.emptyList();
		}
		return items;
	}
}
