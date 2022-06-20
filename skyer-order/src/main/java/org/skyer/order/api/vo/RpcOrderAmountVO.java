package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xurongfa 2022-04-22 11:01
 */
@Data
public class RpcOrderAmountVO {
    @ApiModelProperty("价格类型")
    private String type;

    @ApiModelProperty("金额")
    private BigDecimal amount;
}
