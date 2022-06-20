package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description
 * @Author wusc
 * @create 2022/2/10 4:17 下午
 */
@Data
public class ThirdAmountInfoVO {
    @ApiModelProperty(value = "总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "优惠总金额")
    private BigDecimal totalDiscount;

    @ApiModelProperty(value = "买家实付")
    private BigDecimal buyerPayAmount;

    @ApiModelProperty(value = "应收总金额")
    private BigDecimal amountReceivable;

    @ApiModelProperty(value = "商家优惠金额")
    private BigDecimal businessDiscountAmount;

    @ApiModelProperty(value = "三方承担优惠金额")
    private BigDecimal thirdDiscountAmount;
}
