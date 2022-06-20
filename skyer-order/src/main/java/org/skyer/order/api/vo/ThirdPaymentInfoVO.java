package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description
 * @Author wusc
 * @create 2022/2/10 4:18 下午
 */
@Data
public class ThirdPaymentInfoVO {
    @ApiModelProperty(value = "支付类型")
    private String payType;

    @ApiModelProperty(value = "支付状态")
    private String payStatus;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal payAmount;
}
