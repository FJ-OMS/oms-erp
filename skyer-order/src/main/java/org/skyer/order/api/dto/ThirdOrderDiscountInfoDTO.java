package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zlb
 */
@Data
public class ThirdOrderDiscountInfoDTO {

    @ApiModelProperty(value = "是否有优惠标识")
    private Integer discountFlag;
    @ApiModelProperty(value = "优惠总金额")
    private BigDecimal totalDiscount;
    @ApiModelProperty(value = "商家优惠金额")
    private BigDecimal businessDiscountAmount;
    @ApiModelProperty(value = "三方承担优惠金额")
    private BigDecimal thirdDiscountAmount;

}
