package org.skyer.order.app.service.saga;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import io.camunda.zeebe.client.ZeebeClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Map;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.order.api.dto.ThirdPartyAfterSaleDTO;
import org.skyer.order.app.service.OrderEsService;
import org.skyer.order.domain.entity.ThirdPartyAfterSaleHeader;
import org.skyer.order.domain.repository.AfterSaleHeaderRepository;
import org.skyer.order.domain.repository.ThirdPartyAfterSaleHeaderRepository;
import org.skyer.order.infra.common.CommonConstants;
import org.skyer.saga.saga.SagaDefinition;
import org.skyer.saga.saga.annotation.SagaTask;
import org.skyer.saga.saga.producer.StartSagaBuilder;
import org.skyer.saga.saga.producer.TransactionalProducer;

/**
 * @author: lzh
 * @date: 2022-04-24
 * @description:
 */
@Service
@Slf4j
public class ThirdPartyAfterSaleSagaReturnService {
	private static final Logger logger = LoggerFactory.getLogger(ThirdPartyAfterSaleSagaReturnService.class);

	@Autowired
	private TransactionalProducer transactionalProducer;
	@Autowired
	private ThirdPartyAfterSaleHeaderRepository thirdPartyAfterSaleHeaderRepository;
	@Autowired
	private AfterSaleHeaderRepository afterSaleHeaderRepository;
	@Autowired
	private OrderEsService orderEsService;


	private static final String OUT_REFUND_NO = "outRefundNo";
	private static final String OUT_ORDER_NO = "outOrderNo";
	private static final String INNER_REFUND_NO = "innerRefundNo";
	private static final String INNER_ORDER_NO = "innerOrderNo";

	public void createThirdPartyReturnSaga(ThirdPartyAfterSaleDTO thirdPartyAfterSaleDTO) {
		transactionalProducer.apply(StartSagaBuilder.newBuilder().withLevel(ResourceLevel.SITE).withRefId(thirdPartyAfterSaleDTO.getThirdPartyRefundBaseInfo().getOutRefundNo())
				.withRefType(CommonConstants.SagaRefType.CREATE_THIRD_PARTY_RETURN_ORDER).withPayloadAndSerialize(thirdPartyAfterSaleDTO).withSagaCode("create-third-party-return"), build -> {
			build.withPayloadAndSerialize(thirdPartyAfterSaleDTO).withRefId("outRefundCode:" + thirdPartyAfterSaleDTO.getThirdPartyRefundBaseInfo().getOutRefundNo() + "time:" + System.currentTimeMillis());
		});
	}

	@SagaTask(code = "exec-create-third-return-order", sagaCode = "create-third-party-return", description = "创建三方退货售后单",
			timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID,seq = 1)
	public ThirdPartyAfterSaleHeader sagaTaskCreateThirdPartyReturnStart(String data) {
		logger.info("创建三方退货售后单{}", data);
		ThirdPartyAfterSaleDTO thirdPartyAfterSaleDTO = JSON.parseObject(data).toJavaObject(ThirdPartyAfterSaleDTO.class);
		return thirdPartyAfterSaleHeaderRepository.createThirdPartyRefund(thirdPartyAfterSaleDTO);
	}

	@SagaTask(code = "exec-create-inner-return-header", sagaCode = "create-third-party-return", description = "创建内部退货售后单头",
			timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID,seq = 2)
	public Map<String, String> sagaTaskCreateInnerReturnHeader(String data) {
		logger.info("创建内部退货售后单头{}", data);
		ThirdPartyAfterSaleHeader baseInfo = JSON.parseObject(data).toJavaObject(ThirdPartyAfterSaleHeader.class);
		String innerRefundNo = afterSaleHeaderRepository.createInnerReturnHeader(baseInfo);
		Map<String, String> map = Maps.newHashMap();
		map.put(OUT_REFUND_NO, baseInfo.getOutRefundNo());
		map.put(OUT_ORDER_NO, baseInfo.getOuterOrderNo());
		map.put(INNER_ORDER_NO, baseInfo.getInnerOrderNo());
		map.put(INNER_REFUND_NO,innerRefundNo);
		return map;
	}

	@SagaTask(code = "compare-third-return-order", sagaCode = "create-third-party-return", description = "第三方退货售单数据校验",
			timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID,seq = 3)
	public Map<String, String> compareThirdReturnOrderData(String data) {
		Map<String, String> params = JSON.parseObject(data, new TypeReference<Map<String, String>>(){});
		afterSaleHeaderRepository.compareThirdReturnOrderData(params.get(OUT_REFUND_NO), params.get(OUT_ORDER_NO));
		return params;
	}

	@SagaTask(code = "inner-return-order-goods", sagaCode = "create-third-party-return", description = "创建内部退货售单商品明细",
			timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID,seq = 4)
	public Map<String, String> createReturnGoodsDetails(String data) {
		Map<String, String> params = JSON.parseObject(data, new TypeReference<Map<String, String>>(){});
		afterSaleHeaderRepository.createReturnOrderDetails(params.get(OUT_REFUND_NO), params.get(OUT_ORDER_NO),params.get(INNER_REFUND_NO),params.get(INNER_ORDER_NO));
		return params;
	}

	@SagaTask(code = "inner-return-order-logistics", sagaCode = "create-third-party-return", description = "创建内部退货售单物流明细",
			timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID,seq = 4)
	public Map<String, String> createReturnLogistics(String data) {
		Map<String, String> params = JSON.parseObject(data, new TypeReference<Map<String, String>>(){});
		afterSaleHeaderRepository.createReturnLogistics(params.get(OUT_REFUND_NO), params.get(OUT_ORDER_NO),params.get(INNER_REFUND_NO),params.get(INNER_ORDER_NO));
		return params;
	}

	@SagaTask(code = "inner-return-order-receiver", sagaCode = "create-third-party-return", description = "创建内部退货售单物流明细",
			timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID,seq = 4)
	public Map<String, String> createReturnReceiver(String data) {
		Map<String, String> params = JSON.parseObject(data, new TypeReference<Map<String, String>>(){});
		afterSaleHeaderRepository.createReturnReceiver(params.get(OUT_REFUND_NO), params.get(OUT_ORDER_NO),params.get(INNER_REFUND_NO),params.get(INNER_ORDER_NO));
		return params;
	}

	@SagaTask(code = "sync-return-order-es", sagaCode = "create-third-party-return", description = "同步订单Es",
			timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID,seq = 5)
	public Map<String, String> syncOrderEs(String data) {
		Map<String, String> params = JSON.parseObject(data, new TypeReference<Map<String, String>>(){});
		orderEsService.batchInsertOrderAndAuditToEs(Collections.singletonList(params.get(INNER_ORDER_NO)));
		return params;
	}
}
