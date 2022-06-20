package org.skyer.order.app.service.impl;

import org.apache.commons.compress.utils.Lists;
import org.skyer.order.api.dto.OrderPaymentInfoDTO;
import org.skyer.order.app.service.PriceService;
import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.entity.Price;
import org.skyer.order.domain.repository.PriceRepository;
import org.skyer.order.infra.enums.PriceTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内部订单价格（聚合）应用服务默认实现
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@Service
public class PriceServiceImpl implements PriceService {
    @Autowired
    private PriceRepository priceRepository;
    @Override
    public void saveOrderPrice(Header header, OrderPaymentInfoDTO orderPaymentInfoDTO) {
        List<Price> priceList = Lists.newArrayList();
        Price buyRealPayPrice = new Price(null,header.getOuterOrderNo(),header.getInnerOrderNo(),PriceTypeEnum.BUYER_REAL_PRICE.getType(),PriceTypeEnum.BUYER_REAL_PRICE.getTypeName(),orderPaymentInfoDTO.getPayAmount(),0);
        Price buyNeedPayPrice = new Price(null,header.getOuterOrderNo(),header.getInnerOrderNo(),PriceTypeEnum.BUYER_NEED_PRICE.getType(),PriceTypeEnum.BUYER_NEED_PRICE.getTypeName(),orderPaymentInfoDTO.getPayAmount(),0);
        Price goodeTotalPrice = new Price(null,header.getOuterOrderNo(),header.getInnerOrderNo(),PriceTypeEnum.GOODS_TOTAL_PRICE.getType(),PriceTypeEnum.GOODS_TOTAL_PRICE.getTypeName(),orderPaymentInfoDTO.getGoodsTotalAmount(),0);
        Price costTotalPrice = new Price(null,header.getOuterOrderNo(),header.getInnerOrderNo(),PriceTypeEnum.COST_TOTAL_PRICE.getType(),PriceTypeEnum.COST_TOTAL_PRICE.getTypeName(),orderPaymentInfoDTO.getCostTotalAmount(),0);
        priceList.add(buyRealPayPrice);
        priceList.add(goodeTotalPrice);
        priceList.add(buyNeedPayPrice);
        priceList.add(costTotalPrice);
        priceRepository.batchInsert(priceList);
    }
}
