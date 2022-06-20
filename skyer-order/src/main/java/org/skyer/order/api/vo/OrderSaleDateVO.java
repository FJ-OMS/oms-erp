package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderSaleDateVO {

    @ApiModelProperty(value = "订单日期")
    private String orderDate;

    @ApiModelProperty(value = "订单数量")
    private Integer orderCount;

    @ApiModelProperty(value = "订单商品数量")
    private Integer orderItemCount;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "逆向单数")
    private Integer reverseCount;

    @ApiModelProperty(value = "逆向单商品数量")
    private Integer reverseItemCount;

    @ApiModelProperty(value = "逆向单金额")
    private BigDecimal reverseAmount;

    @ApiModelProperty(value = "发货订单数")
    private Integer sendOrderCount;

    @ApiModelProperty(value = "发货订单商品数量")
    private Integer sendOrderItemCount;

    @ApiModelProperty(value = "发货订单金额")
    private BigDecimal sendOrderAmount;
}
