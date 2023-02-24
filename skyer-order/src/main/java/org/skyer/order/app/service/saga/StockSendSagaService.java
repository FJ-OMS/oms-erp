package org.skyer.order.app.service.saga;

import com.alibaba.fastjson.JSONObject;
import org.skyer.order.app.dto.OutboundDeliveryOrderDTO;
import org.skyer.order.app.dto.ResultRecordDTO;
import org.skyer.order.app.service.HeaderService;
import org.skyer.saga.saga.SagaDefinition;
import org.skyer.saga.saga.annotation.SagaTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description
 * @Author wusc
 * @create 2022/2/28 9:57 上午
 */
@Service
public class StockSendSagaService {
    @Autowired
    private HeaderService headerService;



    @SagaTask(code = "exe-generate-outbound-delivery-order-retrography", sagaCode = "generate-outbound-delivery-order", description = "执行出库单反写到订单",
            timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1,  concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID, seq = 10)
    public Boolean outboundDeliveryOrderRetrography(String data) {
        OutboundDeliveryOrderDTO dto = JSONObject.parseObject(data, OutboundDeliveryOrderDTO.class);
        return headerService.outboundDeliveryOrderRetrography(dto);
    }

    @SagaTask(code = "exe-result-record-retrography", sagaCode = "accept-result-record", description = "执行出库结果单反写到订单",
            timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1,  concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID, seq = 10)
    public Boolean resultRecordRetrography(String data) {
        ResultRecordDTO dto = JSONObject.parseObject(data, ResultRecordDTO.class);
        return headerService.resultRecordRetrography(dto);
    }
}
