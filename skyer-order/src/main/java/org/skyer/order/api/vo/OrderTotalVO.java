package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单统计
 * @description
 * @Author wusc
 * @create 2022/2/9 5:15 下午
 */
@Data
public class OrderTotalVO {

    private OrderStatusTotalVO orderStatusTotalVO;

    private UnfinishedTotalVO unfinishedTotalVO;

    private WaitDealTotalVO waitDealTotalVO;

    private OrderInfoVO orderInfoVO;

    private ReceiverInfoVO receiverInfoVO;

    private AmountInfoVO amountInfoVO;

    private PaymentInfoVO paymentInfoVO;



}
