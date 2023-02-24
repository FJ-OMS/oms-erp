package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 第三方订单详情
 * @author  zlb
 */
@Data
public class ThirdOrderDetailVO {
    @ApiModelProperty(value = "订单基础信息")
    private ThirdOrderInfoVO orderInfoVO;
    @ApiModelProperty(value = "收货人信息")
    private ThirdReceiverInfoVO receiverInfoVO;
    @ApiModelProperty(value = "订单金额信息")
    private ThirdAmountInfoVO amountInfoVO;
    @ApiModelProperty(value = "支付信息")
    private ThirdPaymentInfoVO paymentInfoVO;
    @ApiModelProperty(value = "商品信息")
    private List<ThirdItemVO> thirdItemVO;
}
