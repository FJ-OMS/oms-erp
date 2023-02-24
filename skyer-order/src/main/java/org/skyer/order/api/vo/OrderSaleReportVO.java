package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单销售报表
 */
@Data
public class OrderSaleReportVO {

    @ApiModelProperty("渠道")
    private String type;

    @ApiModelProperty("正向订单数量")
    private Integer orderCount;

    @ApiModelProperty("正向订单商品数量")
    private Integer itemCount;

    @ApiModelProperty("正向订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty("逆向订单数量")
    private Integer reverseOrderCount;

    @ApiModelProperty("逆向订单商品数量")
    private Integer reverseItemCount;

    @ApiModelProperty("逆向订单金额")
    private BigDecimal reverseOrderAmount;

    @ApiModelProperty("净销售件数")
    private Integer netSalesItemCount;

    @ApiModelProperty("净销售金额")
    private BigDecimal netSalesAmount;

    @ApiModelProperty("销售总成本")
    private Integer itemCost;

    @ApiModelProperty("销售毛利")
    private BigDecimal itemProfit;
}
