package org.skyer.order.app.service.component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import org.skyer.order.app.service.AfterSaleHeaderService;

/**
 * @author: lzh
 * @date: 2022-03-24
 * @description: 退货退款流程
 */
@Component
@Slf4j
public class ReturnAndRefundWorkTask {

	@Autowired
	private AfterSaleHeaderService afterSaleHeaderService;

	@ZeebeWorker(type = "add-logistics", fetchVariables = {"id"})
	public void uploadLogistics(JobClient client, ActivatedJob job) {
		Map<String, Object> variablesAsMap = job.getVariablesAsMap();
		Long headerId = (Long) variablesAsMap.get("id");
		afterSaleHeaderService.uploadLogistics(headerId,job.getKey());
	}

	@ZeebeWorker(type = "ware-deal", fetchVariables = {"id"})
	public void wareDeal(JobClient client, ActivatedJob job) {
		Map<String, Object> variablesAsMap = job.getVariablesAsMap();
		Long headerId = (Long) variablesAsMap.get("id");
		afterSaleHeaderService.wareDeal(headerId,job.getKey());
	}

	@ZeebeWorker(type = "financial-confirm", fetchVariables = {"id"})
	public void financialDeal(JobClient client, ActivatedJob job) {
		// TODO 财务确认
		client.newCompleteCommand(job.getKey()).send().join();
	}
}
