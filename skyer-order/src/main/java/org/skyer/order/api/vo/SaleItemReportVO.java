package org.skyer.order.api.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleItemReportVO {

    @ApiModelProperty("名称")
    private String spuName;

    @ApiModelProperty("sku编码")
    private String spuCode;

    @ApiModelProperty("商品分类")
    private String classify;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("订单数量")
    private Integer orderCount;

    @ApiModelProperty("日均订单数")
    private Integer balanceOrderCount;

    @ApiModelProperty("销售商品数")
    private Integer saleCount;

    @ApiModelProperty("日均销售数")
    private Integer balanceSaleCount;

    @ApiModelProperty("销售额")
    private BigDecimal saleAmount;

    @ApiModelProperty("日均销售额")
    private BigDecimal balanceSaleAmount;

    @ApiModelProperty("退款数量")
    private Integer reverseItemCount;

    @ApiModelProperty("日均退款金额")
    private BigDecimal reverseAmount;
}
