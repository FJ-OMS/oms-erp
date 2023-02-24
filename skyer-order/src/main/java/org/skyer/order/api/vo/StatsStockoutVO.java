package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xurongfa 2022-04-24 16:32
 */
@Data
public class StatsStockoutVO {
    @ApiModelProperty("商品名称")
    private String spuName;
    @ApiModelProperty("商品编码")
    private String spuCode;
    @ApiModelProperty("SKU编码")
    private String skuCode;
    @ApiModelProperty("缺货订单数")
    private Long stockoutOrderQty;
    @ApiModelProperty("缺货商品数")
    private Long stockoutGoodsQty;
    @ApiModelProperty("可移仓数量")
    private Long usableQty;

}
