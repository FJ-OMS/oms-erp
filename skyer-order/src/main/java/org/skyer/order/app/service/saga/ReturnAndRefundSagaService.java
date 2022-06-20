package org.skyer.order.app.service.saga;

import com.alibaba.fastjson.JSON;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.skyer.core.iam.ResourceLevel;
import org.skyer.order.domain.entity.AfterSaleHeader;
import org.skyer.order.domain.repository.AfterSaleHeaderRepository;
import org.skyer.saga.saga.SagaDefinition;
import org.skyer.saga.saga.annotation.Saga;
import org.skyer.saga.saga.annotation.SagaTask;
import org.skyer.saga.saga.producer.StartSagaBuilder;
import org.skyer.saga.saga.producer.TransactionalProducer;

/**
 * @author: lzh
 * @date: 2022-03-31
 * @description:
 */
@Service
public class ReturnAndRefundSagaService {

	@Autowired
	private TransactionalProducer transactionalProducer;
	@Autowired
	private ZeebeClient client;
	@Autowired
	private AfterSaleHeaderRepository afterSaleHeaderRepository;


	@Saga(description = "创建仅退款工作流", code = "create-refund-work", inputSchemaClass = AfterSaleHeader.class)
	public AfterSaleHeader createRefundWorker(AfterSaleHeader afterSaleHeader) {
		return transactionalProducer.applyAndReturn(StartSagaBuilder.newBuilder().withLevel(ResourceLevel.SITE).withSourceId(1L)
				.withRefType("dto").withPayloadAndSerialize(afterSaleHeader).withSagaCode("create-refund-work"), build -> {
			build.withPayloadAndSerialize(afterSaleHeader).withRefId("RefundOrderCode:" + afterSaleHeader.getInnerRefundNo() + "time:" + System.currentTimeMillis());
			return afterSaleHeader;
		});
	}


	@SagaTask(code = "exec-refund-saga-task", sagaCode = "create-refund-work", description = "退款单创建流程实例",
			concurrentLimitNum = 1,maxRetryCount=0, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE, seq = 1)
	public void execRefundSagaTask(String data) {
		AfterSaleHeader saleHeader = JSON.parseObject(data,AfterSaleHeader.class);
		ProcessInstanceEvent join = client.newCreateInstanceCommand().bpmnProcessId("Process_only_refund").latestVersion().variables(JSON.toJSONString(saleHeader))
				.send().join();
		saleHeader.setBpmnProcessId(join.getBpmnProcessId());
		saleHeader.setBpmnInstanceId(join.getProcessInstanceKey());
		afterSaleHeaderRepository.updateByPrimaryKeySelective(saleHeader);
	}


	@Saga(description = "创建退货退款工作流", code = "create-return-refund-work", inputSchemaClass = AfterSaleHeader.class)
	public AfterSaleHeader  createReturnRefundWorker(AfterSaleHeader afterSaleHeader) {
		return transactionalProducer.applyAndReturn(StartSagaBuilder.newBuilder().withLevel(ResourceLevel.SITE).withSourceId(1L)
				.withRefType("dto").withPayloadAndSerialize(afterSaleHeader).withSagaCode("create-return-refund-work"), build -> {
			build.withPayloadAndSerialize(afterSaleHeader).withRefId("ReturnOrderCode:" + afterSaleHeader.getInnerRefundNo() + "time:" + System.currentTimeMillis());
			return afterSaleHeader;
		});
	}

	@SagaTask(code = "exec-return-refund-task", sagaCode = "create-return-refund-work", description = "退货退款单创建流程实例",
			concurrentLimitNum = 1,maxRetryCount=0, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE, seq = 1)
	public void execReturnRefundSagaTask(String data) {
		AfterSaleHeader afterSaleHeader = JSON.parseObject(data,AfterSaleHeader.class);
		ProcessInstanceEvent join = client.newCreateInstanceCommand().bpmnProcessId("Process_refunds_return").latestVersion().variables(JSON.toJSONString(afterSaleHeader))
				.send().join();
		afterSaleHeader.setBpmnProcessId(join.getBpmnProcessId());
		afterSaleHeader.setBpmnInstanceId(join.getProcessInstanceKey());
		afterSaleHeaderRepository.updateByPrimaryKeySelective(afterSaleHeader);
	}
}
