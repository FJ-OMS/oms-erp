package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountCheckingOrderDTO {

    @ApiModelProperty(value = "平台订单号")
    private String platformOrderNo;

    @ApiModelProperty(value = "内部订单号")
    private String orderNo;

    @ApiModelProperty(value = "内部ID")
    private Long orderId;

    @ApiModelProperty(value = "平台退款单号")
    private String platformRefundNo;

    @ApiModelProperty(value = "内部退款单号")
    private String refundNo;

    @ApiModelProperty(value = "内部退款单ID")
    private Long refundId;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "内部订单金额")
    private BigDecimal orderMoney;

    @ApiModelProperty(value = "订单类型")
    private String orderType;

    @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    @ApiModelProperty(value = "店铺id")
    private Long storeId;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;
}
