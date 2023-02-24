package org.skyer.stock.domain.service.saga.impl;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.skyer.stock.infra.constant.ObjectConstant.LovRecordTypeCode.ADJUST_RECORD;

import org.skyer.core.iam.ResourceLevel;
import org.skyer.saga.saga.producer.StartSagaBuilder;
import org.skyer.saga.saga.producer.TransactionalProducer;
import org.skyer.stock.api.dto.OutboundDeliveryOrderDTO;
import org.skyer.stock.domain.service.saga.StockService;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private TransactionalProducer transactionalProducer;

    @Override
    public void doSkuQtyAdjust(Long id) {
        transactionalProducer.apply(
                StartSagaBuilder
                        .newBuilder()
                        .withLevel(ResourceLevel.SITE)
                        .withSourceId(1L)
                        .withRefType(ADJUST_RECORD)
                        .withSagaCode("do-sku-qty-adjust-2")
                        .withRefId("AdjustRecordId" + id)
                        .withPayloadAndSerialize(id),
                build -> {
                });
    }

    @Override
    public void acceptResultRecord(OutboundDeliveryOrderDTO dto) {
        // 随机生成15为数字当运单号
        dto.setShippingNo(RandomStringUtils.randomNumeric(15));
        transactionalProducer.apply(
                StartSagaBuilder
                        .newBuilder()
                        .withLevel(ResourceLevel.SITE)
                        .withSourceId(1L)
                        .withRefType("accept-result-record")
                        .withSagaCode("accept-result-record-2")
                        .withRefId("code:" + dto.getCode() + "time:" + System.currentTimeMillis())
                        .withPayloadAndSerialize(dto),
                build -> {
                });
    }
}

