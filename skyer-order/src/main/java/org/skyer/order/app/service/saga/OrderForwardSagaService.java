package org.skyer.order.app.service.saga;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.sql.Order;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.extern.slf4j.Slf4j;
import org.skyer.boot.platform.code.constant.CodeConstants;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.order.api.dto.OrderItemDTO;
import org.skyer.order.api.dto.OrderTableDTO;
import org.skyer.order.app.dto.BaseListDTO;
import org.skyer.order.app.service.HeaderService;
import org.skyer.order.app.service.ItemService;
import org.skyer.order.app.service.OrderEsService;
import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.entity.ItemSnapshot;
import org.skyer.order.domain.repository.HeaderRepository;
import org.skyer.order.domain.repository.ItemRepository;
import org.skyer.order.infra.common.CommonConstants;
import org.skyer.order.infra.feign.dto.SearchOrderDTO;
import org.skyer.order.infra.feign.entity.generate.odo.in.OutboundDeliveryOrderInfoDTO;
import org.skyer.order.infra.feign.vo.OrderSkuVO;
import org.skyer.saga.saga.SagaDefinition;
import org.skyer.saga.saga.annotation.Saga;
import org.skyer.saga.saga.annotation.SagaTask;
import org.skyer.saga.saga.producer.StartSagaBuilder;
import org.skyer.saga.saga.producer.TransactionalProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderForwardSagaService {
    @Autowired
    private TransactionalProducer transactionalProducer;
    @Autowired
    private OrderEsService orderEsService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private HeaderService headerService;

    public void createInnerOrder(OrderTableDTO orderTableDTO) {
        transactionalProducer.apply(StartSagaBuilder.newBuilder().withLevel(ResourceLevel.SITE).withRefId(orderTableDTO.getOrderBaseInfoDTO().getOuterOrderNo())
                .withRefType(CommonConstants.SagaRefType.CREATE_INNER_ORDER).withPayloadAndSerialize(orderTableDTO).withSagaCode("create-inner-order"), build -> {
            build.withPayloadAndSerialize(orderTableDTO).withRefId("outOrderCode:" + orderTableDTO.getOrderBaseInfoDTO().getOuterOrderNo() + "time:" + System.currentTimeMillis());
        });
    }

    @SagaTask(code = "exec-create-inner-order", sagaCode = "create-inner-order", description = "创建内部订单头",
            timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID,seq = 1)
    public Map<String,String> execCreateInnerOrder(String data) {
        OrderTableDTO orderTableDTO = JSONObject.parseObject(data).toJavaObject(OrderTableDTO.class);
        BaseListDTO<String, OrderItemDTO> baseListDTO = headerService.createHandOrder(orderTableDTO);
        Map<String,String> map = new HashMap<>();
        map.put("innerOrderCode",baseListDTO.getEntity());
        return map;
    }

    @SagaTask(code = "detail-price-share", sagaCode = "create-inner-order", description = "商品详情的价格均摊",
            timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1,
            concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID, seq = 21)
    public Map<String,String> detailPriceShare(String data) {
        Map<String,String> map = JSONObject.parseObject(data).toJavaObject(Map.class);
        itemService.detailPriceShare(map.get("innerOrderCode"));
        headerService.createSendNotice(map.get("innerOrderCode"));
        return map;
    }

    @SagaTask(code = "exec-inner-order-es", sagaCode = "create-inner-order", description = "内部订单创建成功后，同步到ES",
            timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 3, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID,seq = 22)
    public Map<String,String> execInnerOrderToEs(String data) {
        Map<String,String> map = JSONObject.parseObject(data).toJavaObject(Map.class);
        orderEsService.batchInsertOrderAndAuditToEs(Collections.singletonList(map.get("innerOrderCode")));
        return map;
    }

    @Saga(code = "generate-outbound-delivery-order-2", description = "生成出库单", inputSchemaClass = OutboundDeliveryOrderInfoDTO.class)
    public OutboundDeliveryOrderInfoDTO generateOutboundDeliveryOrder(OutboundDeliveryOrderInfoDTO dto) {
        return transactionalProducer.applyAndReturn(StartSagaBuilder.newBuilder().withLevel(ResourceLevel.SITE).withSourceId(1L)
                .withRefType("dto").withPayloadAndSerialize(dto).withSagaCode("generate-outbound-delivery-order"), build -> {
            build.withPayloadAndSerialize(dto).withRefId("orderCode:" + dto.getOrderCode() + "time:" + System.currentTimeMillis()
            );
            return dto;
        });
    }
}
