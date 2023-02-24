package org.skyer.order.app.service.saga;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.skyer.core.iam.ResourceLevel;
import org.skyer.order.api.dto.WarehouseWarrantSaveDTO;
import org.skyer.order.api.vo.WarehouseWarrantResultVO;
import org.skyer.order.app.service.AfterSaleHeaderService;
import org.skyer.order.domain.entity.AfterSaleHeader;
import org.skyer.order.infra.common.CommonConstants;
import org.skyer.saga.saga.SagaDefinition;
import org.skyer.saga.saga.annotation.Saga;
import org.skyer.saga.saga.annotation.SagaTask;
import org.skyer.saga.saga.producer.StartSagaBuilder;
import org.skyer.saga.saga.producer.TransactionalProducer;

/**
 * @author lzh
 */
@Service
@Slf4j
public class ReturnGoodsWarehouseSagaService {
	private static final Logger logger = LoggerFactory.getLogger(ReturnGoodsWarehouseSagaService.class);


	@Autowired
	private TransactionalProducer transactionalProducer;
	@Autowired
	private AfterSaleHeaderService afterSaleHeaderService;

	@Saga(code = "return-goods-warehousing-warrant", description = "退货入库", inputSchemaClass = AfterSaleHeader.class)
	public void createReturnGoodsWarehousingNoticePrepare(AfterSaleHeader afterSaleHeader) {
		transactionalProducer.apply(
				StartSagaBuilder
						.newBuilder()
						.withLevel(ResourceLevel.SITE)
						.withRefId(afterSaleHeader.getOuterRefundNo())
						.withRefType(CommonConstants.SagaRefType.RETURN_GOODS_WAREHOUSING)
						.withPayloadAndSerialize(afterSaleHeader)
						.withSagaCode("return-goods-warehousing-warrant"),
				builder -> {
					builder
							.withPayloadAndSerialize(afterSaleHeader)
							.withRefId("outRefundCode:" + afterSaleHeader.getOuterRefundNo()+ "time:" + System.currentTimeMillis());
		});
	}

	@SagaTask(code = "create-return-goods-warehousing-notice", sagaCode = "return-goods-warehousing-warrant", description = "订单中心发起入库通知",
			timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID,seq = 1)
	@Transactional(rollbackFor = Exception.class)
	public WarehouseWarrantSaveDTO createReturnGoodsWarehousingNotice(String data) {
		logger.info("订单中心发起入库通知{}", data);
		AfterSaleHeader afterSaleHeader = JSON.parseObject(data).toJavaObject(AfterSaleHeader.class);
		return afterSaleHeaderService.createReturnGoodsWarehousingNotice(afterSaleHeader);
	}

	@SagaTask(code = "return-goods-result-write-back", sagaCode = "return-goods-warehousing-warrant", description = "订单中心入库结果单回写",
			timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID,seq = 10)
	public void writeBackResult(String data) {
		logger.info("入库结果单{}", data);
		WarehouseWarrantResultVO warehouseWarrantResultVO = JSON.parseObject(data).toJavaObject(WarehouseWarrantResultVO.class);
		afterSaleHeaderService.writeBackResult(warehouseWarrantResultVO);
	}
}
