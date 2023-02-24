package org.skyer.order.infra.repository.impl;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.api.dto.AfterSaleItemDTO;
import org.skyer.order.api.vo.AfterSaleItemVO;
import org.skyer.order.domain.entity.AfterSaleItem;
import org.skyer.order.domain.entity.AfterSaleItemLine;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.repository.AfterSaleItemRepository;
import org.skyer.order.infra.mapper.AfterSaleItemMapper;

import cn.hutool.core.collection.CollUtil;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 售后单商品 资源库实现
 *
 * @author lzh 2022-03-16 10:55:40
 */
@Component
public class AfterSaleItemRepositoryImpl extends BaseRepositoryImpl<AfterSaleItem> implements AfterSaleItemRepository {

	@Autowired
	private AfterSaleItemMapper afterSaleItemMapper;

	@Override
	public List<AfterSaleItem> queryByInnerNo(String innerRefundNo) {
		List<AfterSaleItem> items = afterSaleItemMapper.getListByInnerRefundNo(innerRefundNo);
		if (CollUtil.isEmpty(items)) {
			return Collections.emptyList();
		}
		return items;
	}

	@Override
	public Page<AfterSaleItemVO> returnPageByInnerNo(AfterSaleItemDTO afterSaleItemDto) {
		return PageHelper.doPage(afterSaleItemDto.getPage(), afterSaleItemDto.getSize(), () -> afterSaleItemMapper.getReturnListByInnerNo(afterSaleItemDto)) ;
	}

	@Override
	public List<AfterSaleItem> returnGoodsListByItemIds(List<Long> originItemIds) {
		return afterSaleItemMapper.getReturnListByOriginItemIds(originItemIds);
	}

	@Override
	public List<AfterSaleItem> queryByInnerRefundNo(String innerRefundNo) {
		List<AfterSaleItem> items = afterSaleItemMapper.selectByCondition(Condition.builder(AfterSaleItem.class).andWhere(Sqls.custom().andEqualTo(AfterSaleItem.FIELD_INNER_REFUND_NO, innerRefundNo)).build());
		if (CollUtil.isEmpty(items)) {
			return Collections.emptyList();
		}
		return items;
	}


}
