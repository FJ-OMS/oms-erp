package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AfterSaleDetailVO {
    @ApiModelProperty("订单类型")
    private String orderType;
    @ApiModelProperty("商品名称")
    private String spuName;
    @ApiModelProperty("商品编码")
    private String spuCode;
    @ApiModelProperty("SKU编码")
    private String skuCode;
    @ApiModelProperty("订单号")
    private String orderCode;
    @ApiModelProperty("商品行数量")
    private Long qty;
    @ApiModelProperty("退款总金额")
    private BigDecimal refundAmount;
    @ApiModelProperty("退款单价")
    private BigDecimal unitPrice;
}
