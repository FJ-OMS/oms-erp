package org.skyer.order.infra.repository.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.camunda.zeebe.client.ZeebeClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.skyer.order.app.dto.SaleOrderOrderApiDTO;
import org.skyer.order.app.service.AfterSaleHeaderService;
import org.skyer.order.app.vo.ChannelSaleOrderVO;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.skyer.boot.platform.code.builder.CodeRuleBuilder;
import org.skyer.boot.platform.code.constant.CodeConstants;
import org.skyer.boot.platform.lov.annotation.ProcessLovValue;
import org.skyer.core.domain.Page;
import org.skyer.core.exception.CommonException;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.api.dto.*;
import org.skyer.order.api.vo.*;
import org.skyer.order.app.service.OrderEsService;
import org.skyer.order.app.service.saga.ReturnAndRefundSagaService;
import org.skyer.order.domain.entity.*;
import org.skyer.order.domain.repository.*;
import org.skyer.order.infra.common.CommonConstants;
import org.skyer.order.infra.convertor.OrderItemLineConvertor;
import org.skyer.order.infra.convertor.RefundConvertor;
import org.skyer.order.infra.convertor.ThirdPartyAfterSaleConvertor;
import org.skyer.order.infra.enums.AfterSaleOrderException;
import org.skyer.order.infra.enums.OrderItemLineEnum;
import org.skyer.order.infra.enums.PriceTypeEnum;
import org.skyer.order.infra.enums.ReturnRefundEnum;
import org.skyer.order.infra.mapper.AfterSaleHeaderMapper;
import org.skyer.order.infra.util.OrderTagsUtils;

/**
 * 售后单 资源库实现
 *
 * @author lzh 2022-03-16 10:55:39
 */
@Component
@Slf4j
public class AfterSaleHeaderRepositoryImpl extends BaseRepositoryImpl<AfterSaleHeader> implements AfterSaleHeaderRepository {

	@Autowired
	private RefundConvertor refundConvertor;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ItemLineRepository itemLineRepository;
	@Autowired
	private HeaderRepository headerRepository;
	@Autowired
	private ReceiverInfoRepository receiverInfoRepository;
	@Autowired
	private OrderEsService orderEsService;
	@Autowired
	private AfterSaleHeaderMapper afterSaleHeaderMapper;
	@Autowired
	private AfterSaleItemRepository afterSaleItemRepository;
	@Autowired
	private AfterSaleItemLineRepository afterSaleItemLineRepository;
	@Autowired
	private AfterSaleLogisticsRepository afterSaleLogisticsRepository;
	@Autowired
	private AfterSalesReasonRepository afterSalesReasonRepository;
	@Autowired
	private CodeRuleBuilder codeRuleBuilder;
	@Autowired
	private ZeebeClient client;
	@Autowired
	private ReturnAndRefundSagaService returnAndRefundSagaService;
	@Autowired
	private ThirdPartyAfterSaleConvertor thirdPartyAfterSaleConvertor;
	@Autowired
	private ThirdHeaderRepository thirdHeaderRepository;
	@Autowired
	private ThirdPartyAfterSaleHeaderRepository  thirdPartyAfterSaleHeaderRepository;
	@Autowired
	private ThirdPartyAfterSaleItemRepository thirdPartyAfterSaleItemRepository;
	@Autowired
	private OrderItemLineConvertor orderItemLineConvertor;
	@Autowired
	private ItemLinePriceRepository itemLinePriceRepository;
	@Autowired
	private ThirdPartyReturnLogisticsRepository thirdPartyReturnLogisticsRepository;
	@Autowired
	private ThirdPartyAfterReceiverInfoRepository thirdPartyAfterReceiverInfoRepository;
	@Autowired
	private AfterSaleReceiverInfoRepository afterSaleReceiverInfoRepository;
	@Autowired
	private AfterSaleHeaderService afterSaleHeaderService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addRefundOnlyItemLine(AddRefundOnlyDTO addRefundOnlyDto) {
		List<AfterSaleItem> afterSaleItemList = addRefundOnlyDto.getItems();
		if (ObjectUtil.isEmpty(afterSaleItemList)){
			throw new CommonException("请先选中要退款的商品");
		}

		AfterSaleHeader afterSaleHeader = new AfterSaleHeader();
		afterSaleHeader.setInnerRefundNo(addRefundOnlyDto.getInnerRefundNo());
		afterSaleHeader.setOuterRefundNo(addRefundOnlyDto.getOuterRefundNo());
		afterSaleHeader.setInnerOrderNo(addRefundOnlyDto.getInnerOrderNo());
		afterSaleHeader.setOuterOrderNo(addRefundOnlyDto.getOuterOrderNo());

		this.saveItemAndItemLine(afterSaleHeader,CommonConstants.RefundType.REFUND_ONLY,afterSaleItemList,true);

	}

	@Override
	public void addRefundGoodsItemLine(AddRefundOnlyDTO addRefundOnlyDto) {
		List<AfterSaleItem> afterSaleItemList = addRefundOnlyDto.getItems();
		if (ObjectUtil.isEmpty(afterSaleItemList)){
			throw new CommonException("请先选中要退货的商品");
		}

		AfterSaleHeader afterSaleHeader = new AfterSaleHeader();
		afterSaleHeader.setInnerRefundNo(addRefundOnlyDto.getInnerRefundNo());
		afterSaleHeader.setOuterRefundNo(addRefundOnlyDto.getOuterRefundNo());
		afterSaleHeader.setInnerOrderNo(addRefundOnlyDto.getInnerOrderNo());
		afterSaleHeader.setOuterOrderNo(addRefundOnlyDto.getOuterOrderNo());

		this.saveItemAndItemLine(afterSaleHeader,CommonConstants.RefundType.REFUND_AND_RETURN_GOODS,afterSaleItemList,true);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void refundOnlyDeleteItem(AfterItemDeleteDTO afterItemDeleteDto) {
		List<AfterSaleItem> refundItems = afterItemDeleteDto.getItemList();
		if(ObjectUtil.isNotEmpty(refundItems)){

			String innerRefundNo = refundItems.get(0).getInnerRefundNo();
			String innerOrderNo = refundItems.get(0).getInnerOrderNo();

			List<AfterSaleItemLine> deleteItemLine = new ArrayList<>();
			List<AfterSaleItem> noDeleteItems = refundItems.stream().filter(f->ObjectUtil.isNotEmpty(f.getItemLines())).collect(Collectors.toList());
			//仅删除商品行
			noDeleteItems.forEach(item->{
				if (item.getQuantity().equals(1L)){
					throw new CommonException("仅剩一个商品行,不可删除");
				}
				// 设置退款数
				item.setQuantity(item.getQuantity()-item.getItemLines().size());
				deleteItemLine.addAll(item.getItemLines());
			});
			afterSaleItemRepository.batchUpdateByPrimaryKeySelective(noDeleteItems);

			//item和itemLine 数据全删除
			List<AfterSaleItem> deleteItems = refundItems.stream().filter(f->ObjectUtil.isEmpty(f.getItemLines())).collect(Collectors.toList());
			if (ObjectUtil.isNotEmpty(deleteItems)){
				List<AfterSaleItem> afterSaleItems = afterSaleItemRepository.selectByCondition(Condition.builder(AfterSaleItem.class)
						.andWhere(Sqls.custom().andEqualTo(AfterSaleItem.FIELD_INNER_REFUND_NO,innerRefundNo)).build());
				if (afterSaleItems.size()==1){
					throw new CommonException("仅剩一个商品,不可删除");
				}

				deleteItems.forEach(deleteItem->{
					if (deleteItem.getQuantity().equals(1L)){
						throw new CommonException("仅剩一个商品行,不可删除");
					}
				});

				List<Long> originItemIds = deleteItems.stream().map(AfterSaleItem::getOriginItemId).collect(Collectors.toList());
				List<AfterSaleItemLine> itemLine = afterSaleItemLineRepository.selectByCondition(Condition.builder(AfterSaleItemLine.class)
						.andWhere(Sqls.custom().andEqualTo(AfterSaleItemLine.FIELD_INNER_REFUND_NO,innerRefundNo)
								.andIn(AfterSaleItemLine.ORIGIN_ITEM_ID,originItemIds)).build());
				deleteItemLine.addAll(itemLine);
				afterSaleItemRepository.batchDeleteByPrimaryKey(deleteItems);
			}
			afterSaleItemLineRepository.batchDeleteByPrimaryKey(deleteItemLine);

			List<Long> itemLineIds = deleteItemLine.stream().map(AfterSaleItemLine::getOriginItemLineId).collect(Collectors.toList());
			List<Long> originItemIds = refundItems.stream().map(AfterSaleItem::getOriginItemId).collect(Collectors.toList());

			if (Boolean.TRUE.equals(afterItemDeleteDto.getIsOnlyRefund())) {
				// 正向单标签回滚
				this.headerMarkTags(innerOrderNo,false,false);
				this.syncItemsDeliveryStatus(originItemIds, CommonConstants.RefundFlag.NUM_NO, false,CommonConstants.ReturnFlag.NUM_NO);
			}else {
				// 正向单、item标签回滚
				this.headerMarkTags(innerOrderNo,false,true);
				this.syncItemsDeliveryStatus(originItemIds, CommonConstants.RefundFlag.NUM_NO, false,CommonConstants.ReturnFlag.NUM_YES);

				// 刷新es
				orderEsService.batchInsertOrderToEs(Collections.singletonList(innerOrderNo));
			}


		}
	}



	@Override
	@ProcessLovValue
	public Page<RefundOnlyPageVO> refundOnlyPage(RefundOnlyPageDTO refundOnlyPageDto) {
		return PageHelper.doPage(refundOnlyPageDto.getPage(),refundOnlyPageDto.getSize(), ()-> afterSaleHeaderMapper.refundOnlyPage(refundOnlyPageDto));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createOnlyRefund(RefundGoodsDTO refundGoodsDTO) {
		// 第三方单号唯一性校验
		compareOuterFundNo(refundGoodsDTO.getRefundInfoDTO().getOuterRefundNo());


		// 对应关联订单打标、修改状态
		this.markReturnTags(refundGoodsDTO.getOrderRefundInfoDTO().getOriginOrderId());
		OrderRefundInfoDTO orderRefundInfoDTO = refundGoodsDTO.getOrderRefundInfoDTO();
		RefundInfoDTO refundInfoDTO = refundGoodsDTO.getRefundInfoDTO();
		AfterSaleHeader saleHeader = refundConvertor.orderTORefund(orderRefundInfoDTO,refundInfoDTO,new RefundLogisticsDTO());
		// 生成编码
		String generateCode = codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, 0L,
				CommonConstants.CodeRule.INNER_REFUND_NO, CodeConstants.CodeRuleLevelCode.CUSTOM, CommonConstants.LevelValue.INNER_REFUND_NO, null);
		saleHeader.setInnerRefundNo(generateCode);
		// 待审核状态
		saleHeader.setStatus(CommonConstants.RefundAudiStatus.COMPLETE);
		saleHeader.setType(CommonConstants.RefundType.REFUND_ONLY);
        saleHeader.setEndorseStatus(ReturnRefundEnum.REFUND_ONE_WAIT.getCode());
        //保存商品和商品行数据
		this.saveItemAndItemLine(saleHeader, CommonConstants.RefundType.REFUND_ONLY, refundGoodsDTO.getItems(), false);
		this.insertSelective(saleHeader);
		// 同步ES
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				// 刷新es
				orderEsService.batchInsertOrderToEs(Collections.singletonList(orderRefundInfoDTO.getInnerOrderNo()));
			}
		});
	}

	/**
	 * 新增放入item和itemLine数据
	 * @param saleHeader saleHeader
	 * @param type 仅退款还是退货退款
	 * @param items 要新增的item
	 * @param addFlag 新增还是额外增加退货/退款商品
	 */
	private void saveItemAndItemLine(AfterSaleHeader saleHeader,String type,List<AfterSaleItem> items,boolean addFlag){
		log.info("开始保存商品和商品行数据"+ LocalTime.now());
		if (ObjectUtil.isNotEmpty(items)){
			//更新原因被使用次数
			if (!addFlag){
				List<AfterSalesReason> afterSalesReasons =	afterSalesReasonRepository.selectByCondition(Condition.builder(AfterSalesReason.class)
						.andWhere(Sqls.custom().andEqualTo(AfterSalesReason.FIELD_REASON_CODE,saleHeader.getReason())).build());

				afterSalesReasons.forEach(reason->{
					reason.setUseFlag(1);
					reason.setUseNum(reason.getUseNum()==null?1:reason.getUseNum()+1);
				});
				afterSalesReasonRepository.batchUpdateByPrimaryKeySelective(afterSalesReasons);
			}

			List<AfterSaleItemLine> addItemLineList = new ArrayList<>();
			List<AfterSaleItem> addItemList = new ArrayList<>(items);

			addItemList.forEach(item->{
				item.setOuterRefundNo(saleHeader.getOuterRefundNo());
				item.setInnerRefundNo(saleHeader.getInnerRefundNo());
				item.setInnerOrderNo(saleHeader.getInnerOrderNo());
				item.setOuterOrderNo(saleHeader.getOuterOrderNo());
				item.setType(type);
				item.setGiftFlag(item.getGiftFlag());
				item.setQuantity(0L);
			});

			addItemList.stream().filter(f->ObjectUtil.isNotEmpty(f.getItemLines())).forEach(item-> {
				item.setQuantity((long) item.getItemLines().size());
				item.getItemLines().forEach(itemLine->itemLine.setOriginItemId(item.getOriginItemId()));
				addItemLineList.addAll(item.getItemLines());
			});

			List<Long> originItemIds = items.stream().filter(f->ObjectUtil.isEmpty(f.getItemLines())).map(AfterSaleItem::getOriginItemId).collect(Collectors.toList());

			if(ObjectUtil.isNotEmpty(originItemIds)){
				List<AfterSaleItemLine> itemLineList;
				if (CommonConstants.RefundType.REFUND_ONLY.equals(type)){
					itemLineList = afterSaleItemLineRepository.returnListByItemIds(originItemIds);
				}else {
					itemLineList = afterSaleItemLineRepository.returnGoodsListByItemIds(originItemIds);
				}
				//全选退款单的itemLine数
				addItemList.stream().filter(f->ObjectUtil.isEmpty(f.getItemLines())).forEach(item-> {
					long quantity = itemLineList.stream().filter(f->f.getOriginItemId().equals(item.getOriginItemId())).count();
					item.setQuantity(quantity);
				});

				addItemLineList.addAll(itemLineList);
			}
			log.info("查询出所有可退货/退款的itemLine"+ LocalTime.now());

			AtomicReference<BigDecimal> total=new AtomicReference<>(new BigDecimal(0));
			addItemLineList.forEach(itemLine->{
				itemLine.setOuterRefundNo(saleHeader.getOuterRefundNo());
				itemLine.setInnerRefundNo(saleHeader.getInnerRefundNo());
				itemLine.setInnerOrderNo(saleHeader.getInnerOrderNo());
				itemLine.setType(type);
				total.updateAndGet(v -> v.add( Optional.ofNullable(itemLine.getUnitPrice()).orElse(new BigDecimal(0))));
			});
			List<Long> itemIds;
			//避免重复插入item
			if (addFlag){
					List<Long> oldOriginItemIdList = items.stream().map(AfterSaleItem::getOriginItemId).collect(Collectors.toList());
					List<AfterSaleItem> afterSaleItems = afterSaleItemRepository.selectByCondition(Condition.builder(AfterSaleItem.class)
							.andWhere(Sqls.custom().andIn(AfterSaleItem.FIELD_ORIGIN_ITEM_ID,oldOriginItemIdList)).build());
					List<Long> oldOriginItemIds = afterSaleItems.stream().map(AfterSaleItem::getOriginItemId).collect(Collectors.toList());
				List<AfterSaleItem> addAfterSaleItems = addItemList.stream().filter(f->!oldOriginItemIds.contains(f.getOriginItemId())).collect(Collectors.toList());
				afterSaleItemRepository.batchInsertSelective(addAfterSaleItems);
				itemIds = addAfterSaleItems.stream().map(AfterSaleItem::getOriginItemId).filter(Objects::nonNull).collect(Collectors.toList());
				//更新退款数
				afterSaleItems.forEach(updateItem->{
					long addNum  = addItemLineList.stream().filter(f->f.getOriginItemId().equals(updateItem.getOriginItemId())).count();
					updateItem.setQuantity(updateItem.getQuantity()+addNum);
				});
				afterSaleItemRepository.batchUpdateByPrimaryKeySelective(afterSaleItems);

			}else {
				afterSaleItemRepository.batchInsertSelective(addItemList);
				itemIds = items.stream().map(AfterSaleItem::getOriginItemId).filter(Objects::nonNull).collect(Collectors.toList());
			}
			afterSaleItemLineRepository.batchInsertSelective(addItemLineList);
			log.info("售后数据插入"+ LocalTime.now());

			//并且修改退货/退款状态 商品行打退款标签
			List<Long> itemLineIds = addItemLineList.stream().map(AfterSaleItemLine::getOriginItemLineId).filter(Objects::nonNull).collect(Collectors.toList());
			if (CommonConstants.RefundType.REFUND_ONLY.equals(type)){
				syncItemsLineDeliveryStatus(itemLineIds,CommonConstants.RefundFlag.YES,CommonConstants.ReturnFlag.NUM_NO,true);
				syncItemsDeliveryStatus(itemIds,CommonConstants.RefundFlag.YES,true,CommonConstants.ReturnFlag.NUM_NO);
				log.info("挂起商品行"+ LocalTime.now());
			}else {
				syncItemsLineDeliveryStatus(itemLineIds,CommonConstants.RefundFlag.YES,CommonConstants.ReturnFlag.NUM_YES,true);
				syncItemsDeliveryStatus(itemIds,CommonConstants.RefundFlag.YES,true,CommonConstants.ReturnFlag.NUM_YES);
			}
			// 插入sale总数量
			saleHeader.setGoodsQuantity(addItemLineList.size());
			// 插入sale总金额
			saleHeader.setRefundAmount(total.get());
		}
	}


	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void createRefundGoods(RefundGoodsDTO refundGoodsDTO) {
		// 第三方单号唯一性校验
		compareOuterFundNo(refundGoodsDTO.getRefundInfoDTO().getOuterRefundNo());

		// 对应关联订单打标、修改状态
		this.headerMarkTags(refundGoodsDTO.getOrderRefundInfoDTO().getInnerOrderNo(),true,false);

		OrderRefundInfoDTO orderRefundInfoDTO = refundGoodsDTO.getOrderRefundInfoDTO();
		RefundInfoDTO refundInfoDTO = refundGoodsDTO.getRefundInfoDTO();

		RefundLogisticsDTO dto = refundGoodsDTO.getRefundLogisticsDTO();
		AfterSaleHeader saleHeader = refundConvertor.orderTORefund(orderRefundInfoDTO,refundInfoDTO,dto);
		// 生成编码
		String generateCode = codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, 0L,
				CommonConstants.CodeRule.INNER_REFUND_NO, CodeConstants.CodeRuleLevelCode.CUSTOM, CommonConstants.LevelValue.INNER_REFUND_NO, null);
		saleHeader.setInnerRefundNo(generateCode);
		// 添加物流信息
		List<RefundLogisticsDTO.Logistics> logisticsList = dto.getLogisticsList();
		logisticsList =logisticsList.stream().filter(x->Objects.nonNull(x.getReturnLogisticsNo())).collect(Collectors.toList());
		if (CollUtil.isNotEmpty(logisticsList)){
			addRefundLogisticsInfo(saleHeader, generateCode, logisticsList);
		}
		// 添加收货信息
		AfterSaleReceiveInfoDTO afterSaleReceiveInfoDTO = refundGoodsDTO.getAfterSaleReceiveInfoDTO();
		AfterSaleReceiverInfo afterSaleReceiverInfo = new AfterSaleReceiverInfo();
		BeanUtils.copyProperties(afterSaleReceiveInfoDTO,afterSaleReceiverInfo);
		afterSaleReceiverInfoRepository.insert(afterSaleReceiverInfo);
		// 待审核状态
		saleHeader.setType(CommonConstants.RefundType.REFUND_AND_RETURN_GOODS);
		saleHeader.setEndorseStatus(ReturnRefundEnum.REFUND_RETURN_ONE_WAIT.getCode());
		saleHeader.setStatus(CommonConstants.RefundAudiStatus.COMPLETE);
		//保存商品和商品行数据
		this.saveItemAndItemLine(saleHeader,CommonConstants.RefundType.REFUND_AND_RETURN_GOODS,refundGoodsDTO.getItems(),false);


		this.insertSelective(saleHeader);
		// 同步ES
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				// 刷新es
				orderEsService.batchInsertOrderToEs(Collections.singletonList(orderRefundInfoDTO.getInnerOrderNo()));
			}
		});
	}


	private void compareOuterFundNo(String outerRefundNo) {
		int selectCountByCondition = this.selectCountByCondition(Condition.builder(AfterSaleHeader.class).andWhere(Sqls.custom().andEqualTo(AfterSaleHeader.FIELD_OUTER_REFUND_NO, outerRefundNo)).build());
		if (selectCountByCondition>0){
			throw new CommonException("该第三方单号已创建售后单");
		}
	}


	@Override
	public void syncItemsLineDeliveryStatus(List<Long> itemLineIds, Integer refundFlag, Integer returnFlag, boolean tagsFlag) {
		List<ItemLine> itemLines = itemLineRepository.selectByIds(StringUtils.join(itemLineIds, ","));
		if (CollUtil.isEmpty(itemLines)){
			throw new CommonException("同步商品数据异常");
		}
		List<ItemLine> lines = itemLines.stream().map(item -> {
			ItemLine line = new ItemLine();
			line.setId(item.getId());
			line.setRefundFlag(refundFlag);
			line.setReturnFlag(returnFlag);
			// 打标或者去掉退货退款标签 tagsFlag为true添加标签
			String tags = dealTags(item.getTags(),tagsFlag,returnFlag);
			line.setTags(tags);
			return line;
		}).collect(Collectors.toList());
		itemLineRepository.batchUpdateByPrimaryKeySelective(lines);
	}

	@Override
	public List<AfterSaleDetailVO> getDetail(String refundCode) {
		return afterSaleHeaderMapper.getDetail(refundCode) ;
	}

	@Override
	public AfterSaleHeader queryByRefundNo(String innerRefundNo) {
		List<AfterSaleHeader> afterSaleHeaders = afterSaleHeaderMapper.selectByCondition(Condition.builder(AfterSaleHeader.class)
					.andWhere(Sqls.custom().andEqualTo(AfterSaleHeader.FIELD_INNER_REFUND_NO, innerRefundNo)).build());
		if (CollectionUtils.isEmpty(afterSaleHeaders)){
			return null;
		}
		return afterSaleHeaders.get(0);
	}


	@Override
	public void syncItemsDeliveryStatus(List<Long> itemIds, Integer refundFlag, boolean tagsFlag,Integer returnFlag) {
		List<Item> items = itemRepository.selectByIds(StringUtils.join(itemIds, ","));
		if (CollUtil.isEmpty(items)){
			throw new CommonException("同步商品数据异常");
		}

		if (tagsFlag){
			List<Item> itemList = items.stream().map(item -> {
				Item itemEntity = new Item();
				itemEntity.setId(item.getId());
				itemEntity.setRefundFlag(refundFlag);
				// 打标或者去掉退货退款标签 tagsFlag为true添加标签
				String tags = dealTags(item.getTags(),tagsFlag,returnFlag);
				itemEntity.setTags(tags);
				return itemEntity;
			}).collect(Collectors.toList());
			itemRepository.batchUpdateByPrimaryKeySelective(itemList);
		}else {
			//获取itemLine数据 判断是否需要删除标签
			List<ItemLine> itemLineList= itemLineRepository.selectByCondition(Condition.builder(ItemLine.class)
					.andWhere(Sqls.custom().andIn(ItemLine.FIELD_INNER_ITEM_ID,itemIds)).build());
			//过滤出需要删除标签的item
			List<Item> deleteTagsItemList = items.stream().filter(item -> {
				long returnNum = itemLineList.stream().filter(itemLine -> itemLine.getInnerItemId().equals(item.getId())&&itemLine.getRefundFlag().equals(CommonConstants.RefundFlag.YES)).count();
				return returnNum == 0;
			}).collect(Collectors.toList());
			log.info("需要删除标签的itemIds:{}",deleteTagsItemList.stream().map(Item::getId).collect(Collectors.toList()));
			if(ObjectUtil.isNotEmpty(deleteTagsItemList)){
				//删除标签
				List<Item> itemList = deleteTagsItemList.stream().map(item -> {
					Item itemEntity = new Item();
					itemEntity.setId(item.getId());
					itemEntity.setRefundFlag(refundFlag);
					// 打标或者去掉退货退款标签 tagsFlag为true添加标签
					String tags = dealTags(item.getTags(),tagsFlag,returnFlag);
					itemEntity.setTags(tags);
					return itemEntity;
				}).collect(Collectors.toList());
				itemRepository.batchUpdateByPrimaryKeySelective(itemList);
			}
		}
	}

	/**
	 * 商品行标签处理
	 * @param tags
	 * @param tagsFlag
	 * @param returnFlag
	 * @return
	 */
	private String dealTags(String tags,boolean tagsFlag,Integer returnFlag) {
		StringBuilder builder = new StringBuilder();
		// 移除标签
		if (!tagsFlag) {
			String newTags = "";
			if (StringUtils.isNotBlank(tags)) {
				List<String> stringList = Arrays.asList(tags.split(","));
				List<String> arrList = new ArrayList<>(stringList);
				arrList.removeAll(Arrays.asList(CommonConstants.OrderTags.REFUND, CommonConstants.OrderTags.RETURN));
				if (!arrList.isEmpty()) {
					newTags = arrList.stream().
							reduce(new StringBuilder(), (sb, s) -> sb.append(s).append(','), StringBuilder::append).toString();
					newTags = newTags.substring(0, newTags.length() - 1);
				}
			}
			return newTags;
		}

		// 添加标签
		if (StringUtils.isNotBlank(tags)) {
			//如果是退货退款
			if (CommonConstants.ReturnFlag.NUM_YES.equals(returnFlag)&&!tags.contains(CommonConstants.OrderTags.RETURN)) {
				return builder.append(tags).append(",").append(CommonConstants.OrderTags.RETURN).toString();
			}else if (!tags.contains(CommonConstants.OrderTags.REFUND)){
				return builder.append(tags).append(",").append(CommonConstants.OrderTags.REFUND).toString();
			}
		} else {
			if (CommonConstants.ReturnFlag.NUM_YES.equals(returnFlag)) {
				return builder.append(CommonConstants.OrderTags.RETURN).toString();
			}else {
				return builder.append(CommonConstants.OrderTags.REFUND).toString();
			}
		}
		return tags;
	}




	private void  addRefundLogisticsInfo(AfterSaleHeader saleHeader, String generateCode, List<RefundLogisticsDTO.Logistics> logisticsList) {
		List<AfterSaleLogistics> collect = logisticsList.stream().map(x -> {
			// 物流单号唯一性校验
			AfterSaleLogistics logistics = new AfterSaleLogistics();
			if (Objects.nonNull(x.getReturnLogisticsNo())){
				logistics.setLogisticsNo(x.getReturnLogisticsNo());
				AfterSaleLogistics afterSaleLogistics = afterSaleLogisticsRepository.selectOne(logistics);
				if (afterSaleLogistics!=null){
					String innerRefundNo = afterSaleLogistics.getInnerRefundNo();
					AfterSaleHeader afterSaleHeader = queryByRefundNo(innerRefundNo);
					if (Objects.nonNull(afterSaleHeader) && !CommonConstants.RefundAudiStatus.REFUSE.equals(afterSaleHeader.getStatus())){
						throw new CommonException("物流单号已存在售后单");
					}
				}
			}
			logistics.setInnerOrderNo(saleHeader.getInnerOrderNo());
			logistics.setOuterOrderNo(saleHeader.getOuterOrderNo());
			logistics.setOuterRefundNo(saleHeader.getOuterRefundNo());
			logistics.setInnerRefundNo(generateCode);
			logistics.setLogisticsCompanyId(x.getReturnLogisticsCompanyId());
			logistics.setLogisticsCompanyName(x.getReturnLogisticsCompanyName());
			return logistics;
		}).collect(Collectors.toList());
		afterSaleLogisticsRepository.batchInsertSelective(collect);
	}

	/**
	 * 标记header为退款单
	 * @param id id
	 */
	private void markReturnTags(Long id) {
		Header header = headerRepository.selectByPrimaryKey(id);
		if (Objects.isNull(header)){
			throw new CommonException("内部销售单不存在");
		}
		String tags = header.getTags();
		StringBuilder sb = new StringBuilder();
		if(ObjectUtil.isEmpty(header.getTags())){
			header.setTags(CommonConstants.OrderTags.REFUND);
		}else {
			if (!tags.contains(CommonConstants.OrderTags.REFUND)){
				header.setTags(sb.append(tags).append(",").append(CommonConstants.OrderTags.REFUND).toString());
			}
		}
		// TODO 退款状态
		header.setRefundStatus(1);
		headerRepository.updateByPrimaryKeySelective(header);
	}


	@Override
	public void headerMarkTags(String innerOrderNo,boolean tagsFlag,boolean returnFlag) {
		Header selectHeader = new Header();
		selectHeader.setInnerOrderNo(innerOrderNo);
		Header header = headerRepository.selectOne(selectHeader);
		if (Objects.isNull(header)){
			throw new CommonException("内部销售单不存在");
		}
		String tags = header.getTags();
		if (tagsFlag){
			//打标
			StringBuilder sb = new StringBuilder();
			if (StringUtils.isNotBlank(tags)) {
				if (!returnFlag&&!tags.contains(CommonConstants.OrderTags.REFUND)){
					header.setTags(sb.append(tags).append(",").append(CommonConstants.OrderTags.REFUND).toString());
				}
				if (returnFlag&&!tags.contains(CommonConstants.OrderTags.RETURN)){
					header.setTags(sb.append(tags).append(",").append(CommonConstants.OrderTags.RETURN).toString());
				}
			}else {
				if (returnFlag){
					header.setTags(sb.append(tags).append(",").append(CommonConstants.OrderTags.RETURN).toString());
				}else {
					header.setTags(sb.append(CommonConstants.OrderTags.RETURN).toString());
				}
			}
			//退货退款状态
			header.setRefundStatus(1);
			if (returnFlag){
				header.setReturnStatus(1);
			}
		}else {
			//去掉标签
			if (StringUtils.isNotBlank(tags)) {
				//找出所有itemLine
				List<ItemLine> itemLines = itemLineRepository.selectByCondition(Condition.builder(ItemLine.class)
						.andWhere(Sqls.custom().andEqualTo(ItemLine.FIELD_INNER_ORDER_NO,header.getInnerOrderNo())).build());

				long refundNum  = itemLines.stream().filter(f->f.getRefundFlag().equals(1)).count();
				long returnNum  = itemLines.stream().filter(f->f.getReturnFlag().equals(1)).count();

				String newTags = "";
				List<String> stringList = Arrays.asList(tags.split(","));
				List<String> arrList = new ArrayList<>(stringList);
				if (refundNum==0){
					arrList.remove(CommonConstants.OrderTags.REFUND);
				}
				if (returnNum==0){
					arrList.remove(CommonConstants.OrderTags.RETURN);
				}
				if (!arrList.isEmpty()) {
					newTags = arrList.stream().
							reduce(new StringBuilder(), (sb, s) -> sb.append(s).append(','), StringBuilder::append).toString();
					newTags = newTags.substring(0, newTags.length() - 1);
				}
				header.setTags(newTags);
			}
		}

		headerRepository.updateByPrimaryKeySelective(header);
	}
	@ProcessLovValue
	public RefundBaseInfoVO setLov(AfterSaleHeader afterSaleHeader) {
		return refundConvertor.changeDetailsBase(afterSaleHeader);
	}

	@Override
	public RefundDetailsVO queryBaseDetail(Long orderId,String code) {
		AfterSaleHeader afterSaleHeader =null;
		if (Objects.nonNull(orderId)){
			afterSaleHeader = this.selectByPrimaryKey(orderId);
		}else{
			afterSaleHeader = this.queryByRefundNo(code);
		}
		RefundDetailsVO refundDetailsVO = new RefundDetailsVO();
		RefundBaseInfoVO refundBaseInfoVO = setLov(afterSaleHeader);
		RefundLogisticsInfoVO refundLogisticsInfoVO =refundConvertor.dtoToLogistics(afterSaleHeader);

		// 查询物流信息
		List<AfterSaleLogistics> logistics = afterSaleLogisticsRepository.selectByCondition(Condition.builder(AfterSaleLogistics.class)
				.andWhere(Sqls.custom().andEqualTo(AfterSaleLogistics.FIELD_INNER_REFUND_NO, afterSaleHeader.getInnerRefundNo())).build());
		if (CollUtil.isNotEmpty(logistics)){
			List<String> logisticsList = Lists.newArrayList();
			logistics.forEach(x->{
				String logisticsCompanyName = x.getLogisticsCompanyName();
				String logisticsNo = x.getLogisticsNo();
				StringBuilder sb = new StringBuilder();
				logisticsList.add(sb.append(logisticsCompanyName).append("-").append(logisticsNo).toString());
			});
			refundLogisticsInfoVO.setLogisticsList(logisticsList);
		}
		refundLogisticsInfoVO.setWarehousingNotice(afterSaleHeader.getWarehouseWarrantCode());
		refundLogisticsInfoVO.setWarehousingResult(afterSaleHeader.getWarehouseResultCode());
		RefundBuyerInfoVO buyerInfoVO = new RefundBuyerInfoVO();
		ReceiverInfo receiverInfo = receiverInfoRepository.queryReceiveInfoByInnerNo(afterSaleHeader.getInnerOrderNo());
		buyerInfoVO.setBuyerNick(afterSaleHeader.getBuyerNick());
		if (ObjectUtil.isNotEmpty(receiverInfo)){
			StringBuilder builder = new StringBuilder();
			buyerInfoVO.setAddress(builder.append(receiverInfo.getProvince()).append(receiverInfo.getCity()).append(receiverInfo.getDistrict()).append(receiverInfo.getAddress()).toString());
			buyerInfoVO.setReceiverName(receiverInfo.getReceiverName());
			buyerInfoVO.setMobile(receiverInfo.getMobile());
		}

		List<AfterSaleItem> afterSaleItems = afterSaleItemRepository.queryByInnerNo(afterSaleHeader.getInnerRefundNo());

		refundDetailsVO.setItems(afterSaleItems);
		refundDetailsVO.setRefundBaseInfoVO(refundBaseInfoVO);
		refundDetailsVO.setRefundBuyerInfoVO(buyerInfoVO);
		refundDetailsVO.setRefundLogisticsInfoVO(refundLogisticsInfoVO);
		return refundDetailsVO;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void editBase(UpdateRefundDTO updateRefundDTO) {
		AfterSaleHeader saleHeader = this.selectByPrimaryKey(updateRefundDTO.getId());
		if (Objects.isNull(saleHeader)){
			throw new CommonException("售后单不存在");
		}
		AfterSaleHeader header = refundConvertor.toSale(updateRefundDTO);
		header.setId(saleHeader.getId());
		// 物流信息修改
		if (CollUtil.isNotEmpty(updateRefundDTO.getLogisticsList())){
			List<RefundLogisticsDTO.Logistics> logisticsList = updateRefundDTO.getLogisticsList();
			AfterSaleLogistics logistics = new AfterSaleLogistics();
			logistics.setInnerRefundNo(saleHeader.getInnerRefundNo());
			List<AfterSaleLogistics> originSaleLogistics = afterSaleLogisticsRepository.select(logistics);
			// 有原始物流信息，则删除原始物流信息
			if (CollUtil.isNotEmpty(originSaleLogistics)){
				afterSaleLogisticsRepository.batchDelete(originSaleLogistics);
			}
			// 添加物流信息
			addRefundLogisticsInfo(saleHeader,saleHeader.getInnerRefundNo(),logisticsList);
			// 售后单为二审通过时修改为待仓库验收 完成当前工作流节点
			if (ReturnRefundEnum.REFUND_RETURN_TWO_SUCCESS.getCode().equals(saleHeader.getEndorseStatus())) {
				header.setStatus(CommonConstants.RefundAudiStatus.WAIT_WAREHOUSE_RECEIVE);
				header.setBuyerReturnTime(new Date());
				// 完成当前节点工作流
				Map<String, Object>  params = Maps.newHashMap();
				params.put("id", saleHeader.getId());
				client.newCompleteCommand(Long.parseLong(saleHeader.getBpmnJobKey())).variables(params).send().join();
			}
		}
		this.updateByPrimaryKeySelective(header);
	}


	@Override
	@ProcessLovValue
	public Page<RefundPageVO>  refundPage(RefundPageDTO refundPageDTO) {
		if (StringUtils.isNotEmpty(refundPageDTO.getLogisticsNo())) {
			AfterSaleLogistics logistics = new AfterSaleLogistics();
			logistics.setLogisticsNo(refundPageDTO.getLogisticsNo());
			AfterSaleLogistics selectOne = afterSaleLogisticsRepository.selectOne(logistics);
			if (Objects.nonNull(selectOne)){
				refundPageDTO.setInnerRefundNo(selectOne.getInnerRefundNo());
			}
		}
		refundPageDTO.setType(CommonConstants.RefundType.REFUND_AND_RETURN_GOODS);
		return PageHelper.doPage(refundPageDTO.getPage(),refundPageDTO.getSize(),
				()-> afterSaleHeaderMapper.refundPage(refundPageDTO));
	}

	@Override
	public List<RefundsLogisticsVO> refundsLogistics(Long id) {
		AfterSaleHeader saleHeader = this.selectByPrimaryKey(id);
		if (Objects.nonNull(saleHeader)){
			List<AfterSaleLogistics> afterSaleLogistics = afterSaleLogisticsRepository.selectByCondition(Condition.builder(AfterSaleLogistics.class)
					.andWhere(Sqls.custom().andEqualTo(AfterSaleLogistics.FIELD_INNER_REFUND_NO, saleHeader.getInnerRefundNo())).build());
			return refundConvertor.dtoToLogisticsVO(afterSaleLogistics);
		}
		return Collections.emptyList();
	}

	@Override
	@ProcessLovValue
	public Page<RefundPageVO> refundAuditPage(RefundAuditPageDTO refundPageDTO) {
		return PageHelper.doPage(refundPageDTO.getPage(),refundPageDTO.getSize(),
				()-> afterSaleHeaderMapper.refundAuditPage(refundPageDTO));
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public void batchAuditRefund(List<AuditRefundDTO> listDTO) {
		List<Long> idLists = listDTO.stream().map(AuditRefundDTO::getAuditId).collect(Collectors.toList());

		// 审核不通过
		if (CommonConstants.STR_NUM_ZERO.equals(listDTO.get(0).getStatus())){
			List<AfterSaleHeader> saleHeaders = idLists.stream().map(item -> {
				AfterSaleHeader saleHeader = new AfterSaleHeader();
				saleHeader.setId(item);
				saleHeader.setStatus(CommonConstants.RefundAudiStatus.REFUSE);
				saleHeader.setEndorseStatus(ReturnRefundEnum.REFUND_RETURN_ONE_FAIL.getCode());
				if (StringUtils.isNotEmpty(listDTO.get(0).getFailReason())) {
					saleHeader.setEndorseRefuseReason(listDTO.get(0).getFailReason());
				}
				return saleHeader;
			}).collect(Collectors.toList());
			super.batchUpdateByPrimaryKeySelective(saleHeaders);
			// TODO 正向的商品商品行回滚
			return;
		}
		// 审核通过处理
		List<AfterSaleHeader> saleHeaders = idLists.stream().map(item -> {
			AfterSaleHeader saleHeader = new AfterSaleHeader();
			saleHeader.setId(item);
			// 判断有无物流信息
			getLogisticsInfo(saleHeader);
			return saleHeader;
		}).collect(Collectors.toList());
		super.batchUpdateByPrimaryKeySelective(saleHeaders);
	}

	@Override
	public void getLogisticsInfo(AfterSaleHeader saleHeader) {
		List<AfterSaleLogistics> info = afterSaleLogisticsRepository.queryByInnerRefundNo(saleHeader.getInnerRefundNo());
		if (!info.isEmpty()){
			saleHeader.setStatus(CommonConstants.RefundAudiStatus.WAIT_WAREHOUSE_RECEIVE);
			saleHeader.setAuditCompleteTime(new Date());
			saleHeader.setBuyerReturnTime(new Date());
		}
		saleHeader.setStatus(CommonConstants.RefundAudiStatus.WAIT_RETURN_GOODS);
		saleHeader.setAuditCompleteTime(new Date());
	}



	@Override
	public void confirmWarehousing(Long id) {
		AfterSaleHeader saleHeader = this.selectByPrimaryKey(id);
		// TODO 临时代码退货单完成
		saleHeader.setStatus(CommonConstants.RefundAudiStatus.COMPLETE);
		saleHeader.setWarehouseReceivingTime(new Date());
		saleHeader.setRefundCompleteTime(new Date());
		saleHeader.setCompleteDate(new Date());
		this.updateByPrimaryKeySelective(saleHeader);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public String createInnerReturnHeader(ThirdPartyAfterSaleHeader baseInfo) {
		// 第三方售后单号唯一性校验
		compareOuterFundNo(baseInfo.getOutRefundNo());
		AfterSaleHeader saleHeader =thirdPartyAfterSaleConvertor.thirdChangeInnerReturnHeader(baseInfo);
		// 生成编码
		String generateCode = codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, 0L,
				CommonConstants.CodeRule.INNER_REFUND_NO, CodeConstants.CodeRuleLevelCode.CUSTOM, CommonConstants.LevelValue.INNER_REFUND_NO, null);
		saleHeader.setInnerRefundNo(generateCode);
		// 待审核状态
		saleHeader.setOuterRefundNo(baseInfo.getOutRefundNo());
		saleHeader.setType(baseInfo.getType());
		saleHeader.setEndorseStatus(ReturnRefundEnum.REFUND_RETURN_ONE_WAIT.getCode());
		saleHeader.setStatus(CommonConstants.RefundAudiStatus.WAIT_REFUND);
		saleHeader.setRefundType(CommonConstants.STR_NUM_ONE);
		saleHeader.setOutReturnTime(baseInfo.getRefundTime());
		saleHeader.setBuyerRemarks(baseInfo.getRemarks());
		saleHeader.setActionType(CommonConstants.OrderActionType.COMMON_RETURN);
		this.insertSelective(saleHeader);
		// 内部退款单号回写到第三方售后单号
		baseInfo.setInnerRefundNo(saleHeader.getInnerRefundNo());
		thirdPartyAfterSaleHeaderRepository.updateByPrimaryKeySelective(baseInfo);
		return generateCode;
	}

	@Override
	public AfterSaleHeader queryByOutRefundNo(String outerRefundNo) {
		List<AfterSaleHeader> afterSaleHeaders = afterSaleHeaderMapper.selectByCondition(Condition.builder(AfterSaleHeader.class)
				.andWhere(Sqls.custom().andEqualTo(AfterSaleHeader.FIELD_OUTER_REFUND_NO, outerRefundNo)).build());
		if (CollectionUtils.isEmpty(afterSaleHeaders)){
			return null;
		}
		return afterSaleHeaders.get(0);
	}

	@Override
	public void compareThirdReturnOrderData(String outReturnNo,String outOrderNo) {
		// 三方售后单
		ThirdPartyAfterSaleHeader thirdPartyAfterSaleHeader = thirdPartyAfterSaleHeaderRepository.queryByOutRefundNo(outReturnNo);
		// 三方内部退货订单
		AfterSaleHeader saleHeader = this.queryByOutRefundNo(outReturnNo);
		if (Objects.isNull(thirdPartyAfterSaleHeader)){
			log.error("第三方退货单号：{}，第三方订单号：{}，没有对应的三方售后单",outReturnNo,outOrderNo);
			throw new CommonException("error.afterSaleHeader.not.exist");
		}
		// 校验关联三方单是否存在以及流转状态是否异常
		ThirdHeader thirdHeader =thirdHeaderRepository.queryByOutOrderNo(outOrderNo);
		if (Objects.isNull(thirdHeader)){
			thirdPartyAfterSaleHeader.setExceptionStatus(CommonConstants.STR_NUM_ONE);
			thirdPartyAfterSaleHeader.setExceptionReason(AfterSaleOrderException.NO_SUCH_OUT_ORDER_EXCEPTION.getMessage());

			saleHeader.setExceptionStatus(CommonConstants.STR_NUM_ONE);
			saleHeader.setExceptionReason(AfterSaleOrderException.NO_SUCH_OUT_ORDER_EXCEPTION.getMessage());
			saleHeader.setStatus(CommonConstants.RefundAudiStatus.ORDER_EXCEPTION);
			this.updateByPrimaryKeySelective(saleHeader);
			thirdPartyAfterSaleHeaderRepository.updateByPrimaryKeySelective(thirdPartyAfterSaleHeader);
			return;
		}
		if(!thirdHeader.getCirculationStatus().equals(AfterSaleOrderException.OUT_ORDER_NORMAL.getCode())) {
			thirdPartyAfterSaleHeader.setExceptionStatus(CommonConstants.STR_NUM_ONE);
			thirdPartyAfterSaleHeader.setExceptionReason(AfterSaleOrderException.OUT_ORDER_STATUS_EXCEPTION.getMessage());

			saleHeader.setExceptionStatus(CommonConstants.STR_NUM_ONE);
			saleHeader.setExceptionReason(AfterSaleOrderException.OUT_ORDER_STATUS_EXCEPTION.getMessage());
			saleHeader.setStatus(CommonConstants.RefundAudiStatus.ORDER_EXCEPTION);
			this.updateByPrimaryKeySelective(saleHeader);
			thirdPartyAfterSaleHeaderRepository.updateByPrimaryKeySelective(thirdPartyAfterSaleHeader);
			return;
		}
		if (!compareItemLineReturnQuantity(thirdPartyAfterSaleHeader)){
			thirdPartyAfterSaleHeader.setExceptionStatus(CommonConstants.STR_NUM_ONE);
			thirdPartyAfterSaleHeader.setExceptionReason(AfterSaleOrderException.SKU_NUM_OVER_EXCEPTION.getMessage());

			saleHeader.setExceptionStatus(CommonConstants.STR_NUM_ONE);
			saleHeader.setExceptionReason(AfterSaleOrderException.SKU_NUM_OVER_EXCEPTION.getMessage());
			saleHeader.setStatus(CommonConstants.RefundAudiStatus.ORDER_EXCEPTION);
			this.updateByPrimaryKeySelective(saleHeader);
			thirdPartyAfterSaleHeaderRepository.updateByPrimaryKeySelective(thirdPartyAfterSaleHeader);
			return;
		}
		// sku金额
		// 校验地址
		// 校验通过给正向单订单头-添加退货标签、退货标识
		if (CommonConstants.RefundType.REFUND_AND_RETURN_GOODS.equals(thirdPartyAfterSaleHeader.getType())){
			markHeaderTagsAndFLag(thirdPartyAfterSaleHeader.getInnerOrderNo(),true);
		}else {
			markHeaderTagsAndFLag(thirdPartyAfterSaleHeader.getInnerOrderNo(),false);
		}
	}

	/**
	 * 校验退货数量  非退货、非退款商品行且已发货数量是否大于需要退货的商品行数量
	 * @param thirdPartyAfterSaleHeader
	 */
	private boolean compareItemLineReturnQuantity(ThirdPartyAfterSaleHeader thirdPartyAfterSaleHeader) {

		List<ItemLine> lines = itemLineRepository.queryItemLineListByInner(thirdPartyAfterSaleHeader.getInnerOrderNo());

		// 退货校验
		if (CommonConstants.RefundType.REFUND_AND_RETURN_GOODS.equals(thirdPartyAfterSaleHeader.getType())){
		long count = lines.stream().filter(line -> !String.valueOf(OrderItemLineEnum.CANCEL).equals(line.getCurrentStatus()))
				.filter(line -> !line.getReturnFlag().equals(CommonConstants.NUM_ONE))
				.filter(line -> String.valueOf(OrderItemLineEnum.SEND_SUCCESS.getCode()).equals(line.getDeliveryStatus()))
				.count();
			if (count < thirdPartyAfterSaleHeader.getGoodsQuantity()){
				return false;
			}
			return true;
		}

		// 退款校验
		if (CommonConstants.RefundType.REFUND_ONLY.equals(thirdPartyAfterSaleHeader.getType())){
			long count = lines.stream().filter(line -> !String.valueOf(OrderItemLineEnum.CANCEL).equals(line.getCurrentStatus()))
					.filter(line -> !line.getRefundFlag().equals(CommonConstants.NUM_ONE))
					.filter(line -> !String.valueOf(OrderItemLineEnum.SEND_SUCCESS.getCode()).equals(line.getDeliveryStatus()))
					.count();
			if (count < thirdPartyAfterSaleHeader.getGoodsQuantity()){
				return false;
			}
			return true;
		}

		return false;
	}


	/**
	 * 内部订单打退货退款表
	 * @param innerOrderNo
	 * @param returnFlag  true打退货标签，false打退款标签
	 */
	private void markHeaderTagsAndFLag(String innerOrderNo, boolean returnFlag) {
		Header header = headerRepository.queryHeaderByInnerOrderNo(innerOrderNo);
		if (Objects.isNull(header)){
			throw new CommonException("内部销售单不存在");
		}
		String tags = header.getTags();

		if (returnFlag){
			tags=OrderTagsUtils.dealTags(tags, true, Collections.singletonList(CommonConstants.OrderTags.RETURN));
			header.setReturnStatus(CommonConstants.NUM_ONE);
		}else {
			tags=OrderTagsUtils.dealTags(tags, true, Collections.singletonList(CommonConstants.OrderTags.REFUND));
			header.setRefundStatus(CommonConstants.NUM_ONE);
		}
		header.setTags(tags);
		headerRepository.updateByPrimaryKeySelective(header);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createReturnOrderDetails(String outRefundNo, String outOrderNo, String innerRefundNo,String innerOrderNo) {
		AfterSaleHeader saleHeader = this.queryByRefundNo(innerRefundNo);
		if (Objects.isNull(saleHeader)){
			log.error("内部退货单号：{}，无内部售后单",innerRefundNo);
			throw new CommonException(innerRefundNo+"没有对应的内部售后单");
		}
		if (saleHeader.getExceptionStatus().equals(CommonConstants.STR_NUM_ONE)){
			log.info("内部退货单号：{}，数据校验异常，异常原因"+saleHeader.getExceptionReason(), innerRefundNo);
			return;
		}
		// 获取售后单商品信息
		List<ThirdPartyAfterSaleItem> returnItems = thirdPartyAfterSaleItemRepository.queryByOutRefundNo(outRefundNo);
		if (CollectionUtils.isEmpty(returnItems)){
			log.error("第三方退货单号：{}，第三方订单号：{}，没有对应的三方售后单商品",outRefundNo,outOrderNo);
			return;
		}

		// 匹配正向商品行
		List<AfterSaleItemLine> itemLines = matchingInnerOrderItem(innerOrderNo,returnItems,outRefundNo,outOrderNo,innerRefundNo,saleHeader);
		if (CollectionUtils.isEmpty(itemLines)){
			log.error("第三方退货单号：{}，第三方订单号：{}，没有符合退货条件商品行",outRefundNo,outOrderNo);
			return;
		}
		// 填入单价
		List<Long> ids = itemLines.stream().map(AfterSaleItemLine::getOriginItemLineId).collect(Collectors.toList());
		List<Long> itemIds = itemLines.stream().map(AfterSaleItemLine::getOriginItemId).collect(Collectors.toList());
		// 根据商品行ids 获取价格类型-内部均摊价
		List<ItemLinePrice> itemLinePrices = itemLinePriceRepository.queryItemLineIdList(ids, PriceTypeEnum.INNER_BALANCE_PRICE.getType());
		itemLines.forEach(itemLine -> {
			ItemLinePrice itemLinePrice = itemLinePrices.stream().filter(price -> price.getOrderLineId().equals(itemLine.getOriginItemLineId())).findFirst().orElse(null);
			if (Objects.isNull(itemLinePrice)){
				log.info("第三方退货单号：{}，第三方订单号：{}，没有对应的商品行价格",outRefundNo,outOrderNo);
				throw new CommonException("商品行价格不存在");
			}
			itemLine.setUnitPrice(itemLinePrice.getAmount());
		});

		// 根据匹配到的商品行聚合商品信息
		List<AfterSaleItem> afterSaleItems = aggregationItem(itemLines);

		afterSaleItemRepository.batchInsertSelective(afterSaleItems);
		afterSaleItemLineRepository.batchInsert(itemLines);
		// 同步正向单商品、商品行打标
		if (CommonConstants.RefundType.REFUND_AND_RETURN_GOODS.equals(saleHeader.getType())) {
			syncItemsLineDeliveryStatus(ids, CommonConstants.RefundFlag.NUM_NO, CommonConstants.ReturnFlag.NUM_YES, true);
			syncItemsDeliveryStatus(itemIds, CommonConstants.RefundFlag.NUM_NO, true, CommonConstants.ReturnFlag.NUM_YES);
		}else {
			syncItemsLineDeliveryStatus(ids, CommonConstants.RefundFlag.YES, CommonConstants.ReturnFlag.NUM_NO, true);
			syncItemsDeliveryStatus(itemIds, CommonConstants.RefundFlag.YES, true, CommonConstants.ReturnFlag.NUM_NO);
		}
	}

	/**
	 * 根据匹配到的商品行聚合商品信息
	 * @param itemLines
	 * @return
	 */
	private List<AfterSaleItem> aggregationItem(List<AfterSaleItemLine> itemLines) {
		List<AfterSaleItem> afterSaleItems = Lists.newArrayList();
		Map<Long, List<AfterSaleItemLine>> itemLineMaps = itemLines.stream().collect(Collectors.groupingBy(AfterSaleItemLine::getOriginItemId));
		itemLineMaps.forEach((k,v)->{
			AfterSaleItem afterSaleItem = new AfterSaleItem();
			afterSaleItem.setInnerRefundNo(v.get(0).getInnerRefundNo());
			afterSaleItem.setInnerOrderNo(v.get(0).getInnerOrderNo());
			afterSaleItem.setOuterRefundNo(v.get(0).getOuterRefundNo());
			afterSaleItem.setOuterOrderNo(v.get(0).getOuterOrderNo());
			afterSaleItem.setOriginItemId(k);
			afterSaleItem.setSkuCode(v.get(0).getSkuCode());
			afterSaleItem.setSkuName(v.get(0).getSkuName());
			afterSaleItem.setSpuCode(v.get(0).getSpuCode());
			afterSaleItem.setSpuName(v.get(0).getSpuName());
			afterSaleItem.setSpuType(v.get(0).getSpuType());
			afterSaleItem.setSkuId(v.get(0).getSkuId());
			afterSaleItem.setType(v.get(0).getType());
			afterSaleItem.setQuantity((long) v.size());
			afterSaleItem.setUnitPrice(v.get(0).getUnitPrice());
			afterSaleItem.setGiftFlag(v.get(0).getGiftFlag());
			afterSaleItems.add(afterSaleItem);
		});

		return afterSaleItems;
	}

	/**
	 * 匹配正向商品行
	 * @param innerOrderNo
	 * @param returnItems
	 * @return
	 */
	private List<AfterSaleItemLine> matchingInnerOrderItem(String innerOrderNo, List<ThirdPartyAfterSaleItem> returnItems
			,String outRefundNo,String outOrderNo,String innerRefundNo,AfterSaleHeader saleHeader) {
		// 获取订单内部商品行
		List<ItemLine> lines = itemLineRepository.queryItemLineListByInner(innerOrderNo);
		// 取商品行信息逻辑仓ID、名称、编码
		saleHeader.setReceivingWarehouseCode(lines.get(0).getLogicalWarehouseCode());
		saleHeader.setReceivingWarehouseName(lines.get(0).getLogicalWarehouseName());
		saleHeader.setReceivingWarehouseId(lines.get(0).getLogicalWarehouseId());
		saleHeader.setReceivingWarehouseStatus(CommonConstants.STR_NUM_ZERO);
		this.updateByPrimaryKeySelective(saleHeader);
		// 过滤取消、不是已发货的商品行  按skuCode分组
		Map<String, List<ItemLine>> itemLineGroupBySkuCode;
		if (saleHeader.getType().equals(CommonConstants.RefundType.REFUND_AND_RETURN_GOODS)) {
			itemLineGroupBySkuCode = lines.stream()
					.filter(line -> !String.valueOf(OrderItemLineEnum.CANCEL).equals(line.getCurrentStatus()))
					.filter(line -> String.valueOf(OrderItemLineEnum.SEND_SUCCESS.getCode()).equals(line.getDeliveryStatus()))
					.filter(line -> !CommonConstants.NUM_ONE.equals(line.getReturnFlag()))
					.collect(Collectors.groupingBy(ItemLine::getSkuCode));
		}else {
			itemLineGroupBySkuCode = lines.stream()
					.filter(line -> !String.valueOf(OrderItemLineEnum.CANCEL).equals(line.getCurrentStatus()))
					.filter(line -> !String.valueOf(OrderItemLineEnum.SEND_SUCCESS.getCode()).equals(line.getDeliveryStatus()))
					.filter(line -> !CommonConstants.NUM_ONE.equals(line.getRefundFlag()))
					.collect(Collectors.groupingBy(ItemLine::getSkuCode));
		}
		if (MapUtils.isEmpty(itemLineGroupBySkuCode)){
			log.error("第三方退货单号：{}，第三方订单号：{}，没有符合退货条件商品行",outRefundNo,outOrderNo);
			return new ArrayList<>();
		}
		// 匹配商品行
		Map<String, List<ItemLine>> finalItemLineGroupBySkuCode = itemLineGroupBySkuCode;
		return returnItems.parallelStream().flatMap(item -> Stream.iterate(0L, x -> x + 1)
				.limit(item.getQuantity()).map(x -> {
					List<ItemLine> itemLines = finalItemLineGroupBySkuCode.get(item.getSkuCode());
					ItemLine itemLine = itemLines.get(0);
					AfterSaleItemLine afterSaleItemLine = orderItemLineConvertor.changeToAfterSaleItemLine(itemLine);
					afterSaleItemLine.setOriginItemId(itemLine.getInnerItemId());
					afterSaleItemLine.setOriginItemLineId(itemLine.getId());
					afterSaleItemLine.setOuterRefundNo(outRefundNo);
					afterSaleItemLine.setInnerRefundNo(innerRefundNo);
					afterSaleItemLine.setOuterOrderNo(outOrderNo);
					afterSaleItemLine.setType(saleHeader.getType());
					// 移除已匹配的商品行
					itemLines.remove(itemLine);
					finalItemLineGroupBySkuCode.put(itemLine.getSkuCode(), itemLines);
					return afterSaleItemLine;
				})).collect(Collectors.toList());
	}


	private boolean compareInnerOrderException(String innerRefundNo) {
		AfterSaleHeader saleHeader = this.queryByRefundNo(innerRefundNo);
		if (Objects.isNull(saleHeader)){
			log.error("内部退货单号：{}，没有对应的内部售后单",innerRefundNo);
			throw new CommonException(innerRefundNo+"没有对应的内部售后单");
		}
		if (saleHeader.getExceptionStatus().equals(CommonConstants.STR_NUM_ONE)){
			log.info("内部退货单号：{}，数据校验异常，异常原因"+saleHeader.getExceptionReason(), innerRefundNo);
			return true;
		}
		return false;
	}

	/**
	 * 创建内部退货物流信息
	 * @param outRefundNo
	 * @return
	 */
	@Override
	public void createReturnLogistics(String outRefundNo, String outOrderNo, String innerRefundNo,String innerOrderNo) {
		// 判断内部售后单是否异常
		if (compareInnerOrderException(innerRefundNo)){
			return;
		}
		ThirdPartyReturnLogistics thirdPartyReturnLogistics = thirdPartyReturnLogisticsRepository.queryByOuterRefundNo(outRefundNo);
		if (Objects.isNull(thirdPartyReturnLogistics)){
			log.info("第三方退货单号：{}，第三方订单号：{}，没有对应的退货物流信息",outRefundNo,outOrderNo);
			return;
		}
		// 创建内部退货物流信息
		AfterSaleLogistics afterSaleLogistics = new AfterSaleLogistics();
		afterSaleLogistics.setLogisticsNo(thirdPartyReturnLogistics.getLogisticsNo());
		afterSaleLogistics.setLogisticsCompanyName(thirdPartyReturnLogistics.getLogisticsCompanyName());
		afterSaleLogistics.setLogisticsCompanyId(thirdPartyReturnLogistics.getLogisticsCompanyId());
		afterSaleLogistics.setInnerOrderNo(innerOrderNo);
		afterSaleLogistics.setInnerRefundNo(innerRefundNo);
		afterSaleLogistics.setOuterRefundNo(outRefundNo);
		afterSaleLogistics.setOuterOrderNo(outOrderNo);
		afterSaleLogisticsRepository.insert(afterSaleLogistics);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createReturnReceiver(String outRefundNo, String outOrderNo, String innerRefundNo,String innerOrderNo) {
		// 判断内部售后单是否异常
		if (compareInnerOrderException(innerRefundNo)){
			return;
		}
		ThirdPartyAfterReceiverInfo thirdPartyAfterReceiverInfo = thirdPartyAfterReceiverInfoRepository.queryByOuterRefundNo(outRefundNo);
		if (Objects.isNull(thirdPartyAfterReceiverInfo)){
			log.info("第三方退货单号：{}，第三方订单号：{}，没有对应的退货收货人信息",outRefundNo,outOrderNo);
			return;
		}
		// 添加内部退货收货人信息
		AfterSaleHeader saleHeader = this.queryByRefundNo(innerRefundNo);
		if (Objects.isNull(saleHeader)){
			log.error("内部退货单号：{}，没有对应的内部售后单",innerRefundNo);
			throw new CommonException("error.afterSaleHeader.not.exist");
		}
		saleHeader.setReceiverName(thirdPartyAfterReceiverInfo.getReturnName());
		saleHeader.setMobile(thirdPartyAfterReceiverInfo.getMobile());
		saleHeader.setAddress(thirdPartyAfterReceiverInfo.getProvince()+thirdPartyAfterReceiverInfo.getCity()+thirdPartyAfterReceiverInfo.getDistrict()+thirdPartyAfterReceiverInfo.getAddress());

		this.updateByPrimaryKeySelective(saleHeader);

		AfterSaleReceiverInfo afterSaleReceiverInfo = new AfterSaleReceiverInfo();
		BeanUtils.copyProperties(thirdPartyAfterReceiverInfo, afterSaleReceiverInfo);
		afterSaleReceiverInfo.setInnerOrderNo(innerOrderNo);
		afterSaleReceiverInfo.setInnerRefundNo(innerRefundNo);
		afterSaleReceiverInfoRepository.insert(afterSaleReceiverInfo);
	}

	@Override
	public List<OrderSaleReportVO> queryAfterHeaderToSaleReport(OrderSaleDTO orderSaleDTO) {
		List<OrderSaleReportVO> orderSaleReportVOS = afterSaleHeaderMapper.queryAfterHeaderToSaleReport(orderSaleDTO);
		return orderSaleReportVOS;
	}



	@Override
	public List<ChannelSaleOrderVO> queryPartAfterChannelSaleReport(SaleOrderOrderApiDTO saleOrderOrderApiDTO) {
		return afterSaleHeaderMapper.queryParReverseChannelSaleReport(saleOrderOrderApiDTO.getStartDate(),saleOrderOrderApiDTO.getEndDate(),saleOrderOrderApiDTO.getStoreCode());
	}

	@Override
	public List<OrderSaleDateVO> queryOrderSaleDateReport(OrderSaleDTO orderSaleDTO) {
		return afterSaleHeaderMapper.queryAfterOrderSaleDateReport(orderSaleDTO);
	}
	@Override
	public void updateWarehouse(UpdateWarehouseDTO updateWarehouseDTO) {
		AfterSaleHeader afterSaleHeader = this.selectByPrimaryKey(updateWarehouseDTO.getId());
		if (Objects.nonNull(afterSaleHeader)){
			if (!afterSaleHeader.getStatus().equals(CommonConstants.RefundAudiStatus.WAIT_AUDIT)){
				throw new CommonException("当前状态不能更改收货仓库");
			}
			afterSaleHeader.setReceivingWarehouseId(updateWarehouseDTO.getReceivingWarehouseId());
			afterSaleHeader.setReceivingWarehouseCode(updateWarehouseDTO.getReceivingWarehouseCode());
			afterSaleHeader.setReceivingWarehouseName(updateWarehouseDTO.getReceivingWarehouseName());
			this.updateByPrimaryKeySelective(afterSaleHeader);
		}
	}
}
