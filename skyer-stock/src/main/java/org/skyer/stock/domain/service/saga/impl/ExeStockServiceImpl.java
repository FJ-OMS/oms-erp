package org.skyer.stock.domain.service.saga.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.skyer.saga.saga.SagaDefinition;
import org.skyer.saga.saga.annotation.SagaTask;
import org.skyer.stock.api.dto.OutboundDeliveryOrderDTO;
import org.skyer.stock.api.dto.OutboundDeliveryOrderInfoDTO;
import org.skyer.stock.api.dto.ResultRecordDTO;
import org.skyer.stock.domain.service.ComputeAvailableStockService;
import org.skyer.stock.domain.service.OutboundDeliveryOrderService;
import org.skyer.stock.domain.service.ResultRecordService;

@Component
public class ExeStockServiceImpl {

    @Autowired
    private ResultRecordService resultRecordService;
    @Autowired
    private OutboundDeliveryOrderService outboundDeliveryOrderService;
    @Autowired
    private ComputeAvailableStockService computeAvailableStockService;

    @SagaTask(code = "create-result-record-2", sagaCode = "accept-result-record-2", description = "创建结果单",
            concurrentLimitNum = 2, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID, seq = 1)
    public ResultRecordDTO createResultRecord(String data) {
        OutboundDeliveryOrderDTO dto = JSON.parseObject(data, OutboundDeliveryOrderDTO.class);
        return resultRecordService.createResultRecord(dto);
    }

    @SagaTask(code = "result-record-async-es-2", sagaCode = "accept-result-record-2", description = "同步es",
            concurrentLimitNum = 2, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID, seq = 3)
    public void resultRecordAsyncEs(String data) {
        computeAvailableStockService.asyncEs();
    }


    @SagaTask(code = "exe—generate-outbound-delivery-order-2", sagaCode = "generate-outbound-delivery-order-2", description = "生成出库单",
            concurrentLimitNum = 2, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID, seq = 1)
    public OutboundDeliveryOrderDTO generateOutboundDeliveryOrder(String data) {
        OutboundDeliveryOrderInfoDTO dto = JSON.parseObject(data, OutboundDeliveryOrderInfoDTO.class);
        return outboundDeliveryOrderService.generateOutboundDeliveryOrder(dto);
    }

}
