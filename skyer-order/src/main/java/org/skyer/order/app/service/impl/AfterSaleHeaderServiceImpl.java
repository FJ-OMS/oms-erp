package org.skyer.order.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.camunda.zeebe.client.ZeebeClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import org.skyer.boot.platform.code.builder.CodeRuleBuilder;
import org.skyer.boot.platform.code.constant.CodeConstants;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.util.Sqls;
import org.skyer.order.api.dto.*;
import org.skyer.order.api.vo.WarehouseWarrantResultVO;
import org.skyer.order.app.service.*;
import org.skyer.order.app.service.saga.ReturnGoodsWarehouseSagaService;
import org.skyer.order.domain.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.skyer.core.exception.CommonException;
import org.skyer.order.domain.entity.*;
import org.skyer.order.infra.common.CommonConstants;

/**
 * 售后单应用服务默认实现
 *
 * @author lzh 2022-03-16 10:55:39
 */
@Service
@Slf4j
public class AfterSaleHeaderServiceImpl implements AfterSaleHeaderService {

	@Autowired
	private AfterSaleHeaderRepository afterSaleHeaderRepository;
	@Autowired
	private ZeebeClient client;
	@Autowired
	private AfterSaleItemRepository afterSaleItemRepository;
	@Autowired
	private AfterSaleItemLineRepository afterSaleItemLineRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private OrderService orderService;
	@Autowired
	private AfterSaleLogisticsRepository afterSaleLogisticsRepository;
	@Autowired
	private ItemLineRepository itemLineRepository;
	@Autowired
	private OrderEsService orderEsService;
	@Autowired
	private ReturnGoodsWarehouseSagaService returnGoodsWarehouseSagaService;
	@Autowired
	private WarehousingNoticeRepository warehousingNoticeRepository;
	@Autowired
	private AfterSaleReceiverInfoRepository afterSaleReceiverInfoRepository;
	@Autowired
	private CodeRuleBuilder codeRuleBuilder;
	@Autowired
	private AfterSaleHeaderService afterSaleHeaderService;


	/**
	 * 二审通过调用生成入库通知单
	 */
	@Override
	public void doCreateReturnGoodsWarehousingPrepare(AfterSaleHeader afterSaleHeader) {
		// 是否已生成入库通知单
		if (Objects.nonNull(afterSaleHeader.getOrderWarehousingNoticeId())){
			return;
		}
		returnGoodsWarehouseSagaService.createReturnGoodsWarehousingNoticePrepare(afterSaleHeader);
	}

	/**
	 * 审核通过生成入库通知单
	 * @param afterSaleHeader
	 * @return
	 */
	@Override
	public WarehouseWarrantSaveDTO createReturnGoodsWarehousingNotice(AfterSaleHeader afterSaleHeader) {
		// 物流信息
		List<AfterSaleLogistics> logisticsInfo = afterSaleLogisticsRepository.queryByInnerRefundNo(afterSaleHeader.getInnerRefundNo());
		if (CollUtil.isEmpty(logisticsInfo)){
			throw new CommonException("售后单物流信息为空");
		}
		// 商品信息
		List<AfterSaleItem> afterSaleItemLines = afterSaleItemRepository.queryByInnerRefundNo(afterSaleHeader.getInnerRefundNo());
		if (CollUtil.isEmpty(afterSaleItemLines)){
			throw new CommonException("售后单商品信息为空");
		}
		// 获取用户收货人信息
		AfterSaleReceiverInfo afterSaleReceiverInfo = afterSaleReceiverInfoRepository.queryByInnerRefundOrderNo(afterSaleHeader.getInnerRefundNo());
		WarehouseWarrantSaveDTO warehouseWarrantSaveDTO = new WarehouseWarrantSaveDTO();
		// todo租户ID
		warehouseWarrantSaveDTO.setTenantId(0L);
		warehouseWarrantSaveDTO.setInputType(CommonConstants.STR_NUM_ONE);
		if (Objects.nonNull(afterSaleReceiverInfo)){
			warehouseWarrantSaveDTO.setReturnsName(afterSaleReceiverInfo.getReturnName());
			warehouseWarrantSaveDTO.setReturnsAddress(afterSaleReceiverInfo.getAddress());
			warehouseWarrantSaveDTO.setReturnsTel(afterSaleReceiverInfo.getMobile());
			warehouseWarrantSaveDTO.setReturnsMobile(afterSaleReceiverInfo.getMobile());
			warehouseWarrantSaveDTO.setProvince(afterSaleReceiverInfo.getProvince());
			warehouseWarrantSaveDTO.setCity(afterSaleReceiverInfo.getCity());
			warehouseWarrantSaveDTO.setDistrict(afterSaleReceiverInfo.getDistrict());
		}
		warehouseWarrantSaveDTO.setSorBuyerNikeName(afterSaleHeader.getBuyerNick());
		warehouseWarrantSaveDTO.setOrderMsg(afterSaleHeader.getServiceRemarks());
		warehouseWarrantSaveDTO.setBuyMsg(afterSaleHeader.getBuyerRemarks());
		warehouseWarrantSaveDTO.setReceivingLogicWarehouseId(afterSaleHeader.getReceivingWarehouseId());
		warehouseWarrantSaveDTO.setReceivingLogicWarehouseName(afterSaleHeader.getReceivingWarehouseName());
		warehouseWarrantSaveDTO.setReceivingLogicWarehouseCode(afterSaleHeader.getReceivingWarehouseCode());
		warehouseWarrantSaveDTO.setStoreId(afterSaleHeader.getStoreId());
		warehouseWarrantSaveDTO.setStoreName(afterSaleHeader.getStoreName());
		warehouseWarrantSaveDTO.setChannelTypeCode(afterSaleHeader.getChannel());
		warehouseWarrantSaveDTO.setPlatformOrderCode(afterSaleHeader.getOuterOrderNo());
		warehouseWarrantSaveDTO.setOrderCode(afterSaleHeader.getInnerOrderNo());
		warehouseWarrantSaveDTO.setFrontRecordCode(afterSaleHeader.getInnerRefundNo());
		warehouseWarrantSaveDTO.setFrontRecordId(afterSaleHeader.getId());
		List<WarehouseWarrantDetailDTO> items = Lists.newArrayList();
		List<WarehouseWarrantLogisticsDTO> logisticsList = Lists.newArrayList();

		/**
		 * 将logisticsInfo转换为logisticsDTOList
		 */
		logisticsInfo.forEach(logistics -> {
			WarehouseWarrantLogisticsDTO warehouseWarrantLogisticsDTO = new WarehouseWarrantLogisticsDTO();
			warehouseWarrantLogisticsDTO.setLogisticsCompanyId(logistics.getLogisticsCompanyId());
			warehouseWarrantLogisticsDTO.setLogisticsCompanyName(logistics.getLogisticsCompanyName());
			warehouseWarrantLogisticsDTO.setLogisticsNo(logistics.getLogisticsNo());
			logisticsList.add(warehouseWarrantLogisticsDTO);
		});
		/**
		 * 将afterSaleItemLines转换为warehouseWarrantDetailDTOS
		 */
		afterSaleItemLines.forEach(afterSaleItemLine -> {
			WarehouseWarrantDetailDTO warehouseWarrantDetailDTO = new WarehouseWarrantDetailDTO();
			warehouseWarrantDetailDTO.setSkuCode(afterSaleItemLine.getSkuCode());
			warehouseWarrantDetailDTO.setSkuName(afterSaleItemLine.getSkuName());
			warehouseWarrantDetailDTO.setSpuCode(afterSaleItemLine.getSpuCode());
			warehouseWarrantDetailDTO.setSpuName(afterSaleItemLine.getSpuName());
			warehouseWarrantDetailDTO.setPlannedQty(new BigDecimal(afterSaleItemLine.getQuantity()));
			items.add(warehouseWarrantDetailDTO);
		});
		warehouseWarrantSaveDTO.setLogisticsDTOList(logisticsList);
		warehouseWarrantSaveDTO.setDetailList(items);
		// 保存入库通知单并回写到退货单
		WarehousingNotice warehousingNotice = new WarehousingNotice();
		// 生成编码
		String generateCode = codeRuleBuilder.generateCode(CodeConstants.Level.TENANT, 0L,
				CommonConstants.CodeRule.WAREHOUSING_NOTICE, CodeConstants.CodeRuleLevelCode.CUSTOM, CommonConstants.LevelValue.INNER_REFUND_NO, null);
		warehousingNotice.setCode(generateCode);
		warehousingNotice.setInnerOrderNo(afterSaleHeader.getInnerOrderNo());
		warehousingNotice.setInnerRefundNo(afterSaleHeader.getInnerRefundNo());
		warehousingNotice.setStatus(CommonConstants.STR_NUM_ZERO);
		warehousingNotice.setOuterOrderNo(afterSaleHeader.getOuterOrderNo());
		warehousingNotice.setOuterRefundNo(afterSaleHeader.getOuterRefundNo());
		warehousingNotice.setParam(JSON.toJSONString(warehouseWarrantSaveDTO));
		warehousingNoticeRepository.insert(warehousingNotice);
		afterSaleHeader.setOrderWarehousingNoticeId(warehousingNotice.getId());
		afterSaleHeaderRepository.updateByPrimaryKeySelective(afterSaleHeader);
		return warehouseWarrantSaveDTO;
	}

	/**
	 * 入库结果单回写
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void writeBackResult(WarehouseWarrantResultVO warehouseWarrantResultVO) {
		// 对应售后单状态修改为已入库（待财务）
		AfterSaleHeader afterSaleHeader = afterSaleHeaderRepository.queryByRefundNo(warehouseWarrantResultVO.getFrontRecordCode());
		if (Objects.isNull(afterSaleHeader)) {
			throw new CommonException("售后单头不存在");
		}
		afterSaleHeader.setStatus(CommonConstants.RefundAudiStatus.COMPLETE);
		afterSaleHeader.setWarehouseReceivingTime(new Date());
		afterSaleHeader.setReceivingWarehouseStatus(CommonConstants.STR_NUM_ONE);
		afterSaleHeader.setWarehouseWarrantId(warehouseWarrantResultVO.getWarehouseWarrantId());
		afterSaleHeader.setWarehouseWarrantCode(warehouseWarrantResultVO.getWarehouseWarrantCode());
		afterSaleHeader.setWarehouseResultId(warehouseWarrantResultVO.getWarehouseResultId());
		afterSaleHeader.setWarehouseResultCode(warehouseWarrantResultVO.getWarehouseResultCode());
		afterSaleHeaderRepository.updateByPrimaryKeySelective(afterSaleHeader);

		List<WarehousingNotice> vos = warehousingNoticeRepository.selectByCondition(Condition
				.builder(WarehousingNotice.class)
				.andWhere(Sqls.custom()
						.andEqualTo(WarehousingNotice.FIELD_INNER_REFUND_NO, warehouseWarrantResultVO.getFrontRecordCode()))
						.build());
		if (CollectionUtils.isNotEmpty(vos)) {
			WarehousingNotice warehousingNotice = vos.get(0);
			warehousingNotice.setResult(JSON.toJSONString(warehouseWarrantResultVO));
			warehousingNotice.setStatus(CommonConstants.STR_NUM_ONE);
			warehousingNoticeRepository.updateByPrimaryKeySelective(warehousingNotice);
		}
		// 完成当前节点工作流
		Map<String, Object>  params = Maps.newHashMap();
		params.put("id", afterSaleHeader.getId());
		params.put("flag", "true");
		client.newCompleteCommand(Long.parseLong(afterSaleHeader.getWarehouseJobKey())).variables(params).send().join();
	}


	@Override
	public void uploadLogistics(Long headerId, long key) {
		AfterSaleHeader saleHeader = afterSaleHeaderRepository.selectByPrimaryKey(headerId);
		if (Objects.isNull(saleHeader)) {
			throw new CommonException("售后单不存在");
		}
		// 是否有物流信息
		List<AfterSaleLogistics> info = afterSaleLogisticsRepository.queryByInnerRefundNo(saleHeader.getInnerRefundNo());
		if (CollectionUtils.isEmpty(info)) {
			// 无物流信息 存入当前节点key
			saleHeader.setBpmnJobKey(String.valueOf(key));
			afterSaleHeaderRepository.updateByPrimaryKeySelective(saleHeader);
		}else {
			// 有物流时完成当前节点任务
			Map<String, Object> params = Maps.newHashMap();
			params.put("id", saleHeader.getId());
			client.newCompleteCommand(key).variables(params).send().join();
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void wareDeal(Long headerId, long key) {
		AfterSaleHeader saleHeader = afterSaleHeaderRepository.selectByPrimaryKey(headerId);
		if (Objects.isNull(saleHeader)) {
			throw new CommonException("售后单不存在");
		}
		if (saleHeader.getWarehouseJobKey() == null) {
			// 存储当前节点key
			saleHeader.setWarehouseJobKey(String.valueOf(key));
			afterSaleHeaderRepository.updateByPrimaryKeySelective(saleHeader);
			// 发起入库saga
			afterSaleHeaderService.doCreateReturnGoodsWarehousingPrepare(saleHeader);
		}
	}

}
