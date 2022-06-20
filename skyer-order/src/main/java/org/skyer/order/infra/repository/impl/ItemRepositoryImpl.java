package org.skyer.order.infra.repository.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import org.skyer.order.api.vo.SaleItemReportVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.skyer.core.domain.Page;
import org.skyer.core.exception.CommonException;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.api.dto.*;
import org.skyer.order.api.vo.OrderItemPageVO;
import org.skyer.order.app.service.OrderEsService;
import org.skyer.order.domain.entity.*;
import org.skyer.order.domain.repository.HeaderRepository;
import org.skyer.order.domain.repository.ItemLineRepository;
import org.skyer.order.domain.repository.ItemPriceRepository;
import org.skyer.order.domain.repository.ItemRepository;
import org.skyer.order.infra.common.CommonConstants;
import org.skyer.order.infra.convertor.OrderItemConvertor;
import org.skyer.order.infra.enums.OrderItemEnum;
import org.skyer.order.infra.enums.OrderItemLineEnum;
import org.skyer.order.infra.enums.OrderStatusEnum;
import org.skyer.order.infra.enums.PriceTypeEnum;
import org.skyer.order.infra.mapper.ItemLineMapper;
import org.skyer.order.infra.mapper.ItemMapper;
import org.skyer.order.infra.util.ListUtil;

/**
 * 内部订单商品明细 资源库实现
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@Component
public class ItemRepositoryImpl extends BaseRepositoryImpl<Item> implements ItemRepository {
	private static final Logger logger = LoggerFactory.getLogger(ItemRepositoryImpl.class);

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private OrderItemConvertor orderItemConvertor;
	@Autowired
	private ItemPriceRepository itemPriceRepository;
	@Autowired
	private HeaderRepository headerRepository;
	@Autowired
	private ItemLineMapper itemLineMapper;
	@Autowired
	private ItemLineRepository itemLineRepository;
	@Autowired
	private OrderEsService orderEsService;
	@Autowired
	private ItemRepository itemRepository;

	@Override
	public Map<String, List<Item>> queryItemByInner(List<String> innerOrderList) {
		List<Item> items = itemMapper.selectByCondition(Condition.builder(Item.class).andWhere(Sqls.custom().andIn(Item.FIELD_INNER_ORDER_NO, innerOrderList)).build());
		if (CollUtil.isEmpty(items)) {
			return null;
		}
		return items.stream().collect(Collectors.groupingBy(Item::getInnerOrderNo));
	}

	@Override
	public List<Item> queryItemByInner(String innerOrderNo) {
		List<Item> items = itemMapper.selectByCondition(Condition.builder(Item.class).andWhere(Sqls.custom().andEqualTo(Item.FIELD_INNER_ORDER_NO, innerOrderNo)).build());
		if (CollUtil.isEmpty(items)) {
			return null;
		}
		return items;
	}

	@Override
	public Page<OrderItemPageVO> queryItemPage(ItemSearchDTO itemSearchDTO) {
		Header header = headerRepository.selectByPrimaryKey(itemSearchDTO.getOrderId());
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(itemSearchDTO.getPage());
		pageRequest.setSize(itemSearchDTO.getSize());
		Page<Item> itemPage = PageHelper.doPageAndSort(pageRequest, () -> itemMapper.selectByCondition(Condition.builder(Item.class).andWhere(Sqls.custom()
				.andEqualTo(Item.FIELD_INNER_ORDER_NO, header.getInnerOrderNo())
				.andEqualTo(Item.FIELD_DELIVERY_STATUS, itemSearchDTO.getDeliveryStatus(), true)
				.andEqualTo(Item.FIELD_PREEMPTION_STATUS, itemSearchDTO.getPreemptionStatus(), true)
				.andEqualTo(Item.FIELD_SUSPEND_STATUS, itemSearchDTO.getSuspendStatus(), true)
				.andEqualTo(Item.FIELD_SOURCE_STATUS, itemSearchDTO.getSourceStatus(), true)
				.andEqualTo(Item.FIELD_SYNC_PREEMP_STATUS, itemSearchDTO.getSyncPreempStatus(), true)
				.andEqualTo(Item.FIELD_SYNC_DELIVERY_STATUS, itemSearchDTO.getSyncDeliveryStatus(), true)
				.andEqualTo(Item.FIELD_REFUND_FLAG, itemSearchDTO.getRefundFlag(), true)
				.andEqualTo(Item.FIELD_RECEIVING, itemSearchDTO.getReceiveStatus(), true)
		).build()));
		List<OrderItemPageVO> vos = ListUtil.listConvert(itemPage.getContent(), OrderItemPageVO.class);
		if (CollUtil.isNotEmpty(vos)) {
			setItemPrice(vos);
		}
		Page<OrderItemPageVO> page = new Page<>();
		page.setTotalElements(itemPage.getTotalElements());
		page.setNumberOfElements(itemPage.getNumberOfElements());
		page.setTotalPages(itemPage.getTotalPages());
		page.setNumber(itemPage.getNumber());
		page.setSize(itemPage.getSize());
		page.setContent(vos);
		return page;
	}

	private void setItemPrice(List<OrderItemPageVO> vos) {
		List<Long> idList = vos.stream().map(OrderItemPageVO::getId).collect(Collectors.toList());
		if (CollUtil.isNotEmpty(idList)) {
			Map<Long, Map<String, ItemPrice>> itemPriceMap = itemPriceRepository.queryItemPriceMap(idList);
			//拼接价格
			vos.forEach(orderItemPageVO -> {
				if (itemPriceMap != null && itemPriceMap.get(orderItemPageVO.getId()) != null) {
					Map<String, ItemPrice> itemPriceMapById = itemPriceMap.get(orderItemPageVO.getId());
					orderItemPageVO.setThirdSaleAmount(Optional.ofNullable(itemPriceMapById.get(PriceTypeEnum.OUT_SALE_PRICE.getType())).orElse(new ItemPrice()).getAmount());
					orderItemPageVO.setOutBalancePrice(Optional.ofNullable(itemPriceMapById.get(PriceTypeEnum.OUT_BALANCE_PRICE.getType())).orElse(new ItemPrice()).getAmount());
					orderItemPageVO.setInnerSalePrice(Optional.ofNullable(itemPriceMapById.get(PriceTypeEnum.INNER_SALE_PRICE.getType())).orElse(new ItemPrice()).getAmount());
					orderItemPageVO.setInnerBalancePrice(Optional.ofNullable(itemPriceMapById.get(PriceTypeEnum.INNER_BALANCE_PRICE.getType())).orElse(new ItemPrice()).getAmount());
					orderItemPageVO.setTotalCostAmount(Optional.ofNullable(itemPriceMapById.get(PriceTypeEnum.COST_TOTAL_PRICE.getType())).orElse(new ItemPrice()).getAmount());
					if (itemPriceMapById.get(PriceTypeEnum.COST_TOTAL_PRICE.getType()) != null){
						orderItemPageVO.setCostAmount(Optional.ofNullable(itemPriceMapById.get(PriceTypeEnum.COST_TOTAL_PRICE.getType())).orElse(new ItemPrice()).getAmount()
								.divide(BigDecimal.valueOf(orderItemPageVO.getQuantity()),2, RoundingMode.UP));
					}
					orderItemPageVO.setTransPrice(Optional.ofNullable(itemPriceMapById.get(PriceTypeEnum.TRANS_PRICE.getType())).orElse(new ItemPrice()).getAmount());
				}
			});
		}
	}

	@Override
	public List<Item> queryItemListByItemIds(List<Long> itemIds) {
		return itemMapper.selectByCondition(Condition.builder(Item.class)
				.andWhere(Sqls.custom().andIn(Item.FIELD_ID, itemIds)).build());
	}

	@Override
	public List<SaleItemReportVO> totalOrderItemSaleReport(SaleItemReportDTO saleItemReportDTO) {
		List<SaleItemReportVO> saleItemReportVOS = itemMapper.totalOrderItemSaleReport(saleItemReportDTO);
		return saleItemReportVOS;
	}
}
