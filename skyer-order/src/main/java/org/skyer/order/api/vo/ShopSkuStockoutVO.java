package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xurongfa 2022-03-08 9:29
 */
@Data
public class ShopSkuStockoutVO {
    @ApiModelProperty("店铺id")
    private Long shopId;
    @ApiModelProperty("sku编码")
    private String skuCode;
    @ApiModelProperty("缺货数量")
    private BigDecimal stockoutQty;
}
