package org.skyer.order.app.service.component;


import com.alibaba.fastjson.JSONObject;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeVariablesAsType;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;

import org.skyer.order.app.dto.OrderLineWorkDTO;
import org.skyer.order.app.service.*;
import org.skyer.order.app.vo.WorkExecuteVO;
import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.entity.ItemSnapshot;
import org.skyer.order.domain.repository.HeaderRepository;
import org.skyer.order.domain.repository.ItemSnapshotRepository;
import org.skyer.order.infra.common.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单流程work
 * @description
 * @Author wusc
 * @create 2022/1/14 1:34 下午
 */
@Component
public class OrderWorkTask {

    @Autowired
    private ItemLineService itemLineService;
    @Autowired
    private OrderEsService orderEsService;

    @ZeebeWorker(type = "split-commodity-line",fetchVariables = {"innerOrderNo"})
    public void splitCommodityLineWorker(JobClient client, ActivatedJob job) {
        Map<String, Object> params = job.getVariablesAsMap();
        String innerOrderNo = (String) params.get("innerOrderNo");
        WorkExecuteVO workExecuteVO = null;
        try{
            workExecuteVO = itemLineService.splitItemLineByLock(innerOrderNo);
        }catch (Exception e){
            workExecuteVO = new WorkExecuteVO();
            workExecuteVO.setMessage(e.getMessage());
            params.put("code",CommonConstants.WorkCode.SPLIT_ITEM_LINE_ERROR);
            params.put("workExecuteVO",JSONObject.toJSONString(workExecuteVO));
            client.newCompleteCommand(job.getKey()).variables(params).send().join();
            return;
        }
        params.put("innerOrderNo",innerOrderNo);
        //平拆商品行异常进入异常处理流程
        if (CommonConstants.WorkCode.SPLIT_ITEM_LINE_ERROR.equals(workExecuteVO.getCode())) {
            params.put("code",CommonConstants.WorkCode.SPLIT_ITEM_LINE_ERROR);
            params.put("workExecuteVO",JSONObject.toJSONString(workExecuteVO));
            client.newCompleteCommand(job.getKey()).variables(params).send().join();
            return;
        }
        params.put("code",CommonConstants.WorkCode.NORMAL);
        params.put("businessType",workExecuteVO.getResult());
        client.newCompleteCommand(job.getKey()).variables(params).send().join();
    }




}
