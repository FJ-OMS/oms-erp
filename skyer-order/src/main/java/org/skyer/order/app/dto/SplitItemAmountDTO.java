package org.skyer.order.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SplitItemAmountDTO {

    @ApiModelProperty("商品itemCode/或者商品行id")
    private String codeOrId;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("外部价格")
    private BigDecimal outPrice;

    @ApiModelProperty("内部价格")
    private BigDecimal innerPrice;

    @ApiModelProperty("外部均摊价格")
    private BigDecimal outBalancePrice;

    @ApiModelProperty("内部均摊价格")
    private BigDecimal innerBalancePrice;

    @ApiModelProperty("是否排除--赠品或者随品")
    private Integer isGift;
}
