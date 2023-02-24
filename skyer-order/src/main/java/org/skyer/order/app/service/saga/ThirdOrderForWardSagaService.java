package org.skyer.order.app.service.saga;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import lombok.extern.slf4j.Slf4j;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.order.api.dto.OrderItemDTO;
import org.skyer.order.api.dto.OrderTableDTO;
import org.skyer.order.api.dto.ThirdOrderTableDTO;
import org.skyer.order.app.dto.BaseListDTO;
import org.skyer.order.app.service.*;
import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.repository.HeaderRepository;
import org.skyer.order.domain.repository.ItemRepository;
import org.skyer.order.infra.common.CommonConstants;
import org.skyer.order.infra.feign.dto.SearchOrderDTO;
import org.skyer.order.infra.feign.vo.OrderSkuVO;
import org.skyer.saga.saga.SagaDefinition;
import org.skyer.saga.saga.annotation.SagaTask;
import org.skyer.saga.saga.producer.StartSagaBuilder;
import org.skyer.saga.saga.producer.TransactionalProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author zlb
 */
@Service
@Slf4j
public class ThirdOrderForWardSagaService {
    @Autowired
    private TransactionalProducer transactionalProducer;
    @Autowired
    private ThirdHeaderService thirdHeaderService;
    @Autowired
    private OrderEsService orderEsService;
    @Autowired
    private ThirdOrderEsService thirdOrderEsService;
    @Autowired
    private HeaderRepository headerRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private HeaderService headerService;
    @Autowired
    private ZeebeClient client;
    @Autowired
    private OrderForwardSagaService orderForwardSagaService;

    public void createOuterOrder(ThirdOrderTableDTO thirdOrderTableDto) {
        transactionalProducer.apply(StartSagaBuilder.newBuilder()
                .withLevel(ResourceLevel.SITE)
                .withRefId(thirdOrderTableDto.getThirdOrderBaseInfoDTO().getOuterOrderNo())
                .withRefType(CommonConstants.SagaRefType.CREATE_OUTER_ORDER)
                .withPayloadAndSerialize(thirdOrderTableDto)
                .withSagaCode("create-outer-order"), build -> build.withPayloadAndSerialize(thirdOrderTableDto)
                .withRefId("outerOrderNo:" + thirdOrderTableDto.getThirdOrderBaseInfoDTO().getOuterOrderNo() + "time:" + System.currentTimeMillis()));
    }

    @SagaTask(code = "exec-create-outer-header", sagaCode = "create-outer-order", description = "三方订单信息入库",
            timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID,seq = 1)
    public String  execCreateOuterOrder(String data) {
        ThirdOrderTableDTO thirdOrderTableDto = JSON.parseObject(data).toJavaObject(ThirdOrderTableDTO.class);
        OrderTableDTO orderTableDTO = thirdHeaderService.createThirdOrder(thirdOrderTableDto);
        return JSON.toJSONString(orderTableDTO);
    }

    @SagaTask(code = "exec-outer-to-innerHeader", sagaCode = "create-outer-order", description = "创建内部订单头",
            timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID,seq = 2)
    public BaseListDTO<String, OrderItemDTO> outerToInnerHeader(String data) {
        //创建内部订单头
        OrderTableDTO orderTableDTO = JSON.parseObject(data).toJavaObject(OrderTableDTO.class);
        return headerService.createHandOrder(orderTableDTO);
    }

    
    @SagaTask(code = "exec-detail-price-share", sagaCode = "create-outer-order", description = "商品详情的价格均摊",
            timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID,seq = 22)
    public  Map<String,String> detailPriceShare(String data) {
        Map<String,String> map = JSON.parseObject(data).toJavaObject(Map.class);
        if (ObjectUtil.isEmpty(map)) {
            return Collections.emptyMap();
        }
        itemService.detailPriceShare(map.get("innerOrderCode"));
        return map;
    }

    @SagaTask(code = "exec-order-to-es", sagaCode = "create-outer-order", description = "内部订单创建成功后同步到ES",
            timeoutSeconds = 10,maxRetryCount = 3,concurrentLimitNum = 1, concurrentLimitPolicy = SagaDefinition.ConcurrentLimitPolicy.TYPE_AND_ID,seq = 23)
    public Map<String,String> execInnerOrderToEs(String data) {
        if (ObjectUtil.isEmpty(data)) {
            return Collections.emptyMap();
        }
        Map<String,String> map = JSON.parseObject(data).toJavaObject(Map.class);
        orderEsService.batchInsertOrderAndAuditToEs(Collections.singletonList(map.get("innerOrderCode")));
        //根据内部单号同步第三方es
        thirdOrderEsService.batchInsertOrderToEs(null,Collections.singletonList(map.get("innerOrderCode")));
        return map;
    }



}
