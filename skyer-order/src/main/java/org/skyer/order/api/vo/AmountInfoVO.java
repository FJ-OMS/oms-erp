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
public class AmountInfoVO {
    @ApiModelProperty(value = "商品总金额")
    private BigDecimal goodsTotalAmount;

    @ApiModelProperty(value = "买家支付金额")
    private BigDecimal buyerPayAmount;

    @ApiModelProperty(value = "成本总金额")
    private BigDecimal costAmount;
}
