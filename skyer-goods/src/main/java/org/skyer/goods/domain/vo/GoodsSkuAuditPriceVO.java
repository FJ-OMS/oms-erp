package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsSkuAuditPriceVO {

    @ApiModelProperty(value = "成本价", required = true)
    private BigDecimal costPrice;

    @ApiModelProperty(value = "销售价", required = true)
    private BigDecimal salesPrice;

    @ApiModelProperty(value = "活动价", required = true)
    private BigDecimal activityPrice;

    @ApiModelProperty(value = "市场价", required = true)
    private BigDecimal marketPrice;

    @ApiModelProperty(value = "分销价", required = true)
    private BigDecimal distributionPrice;

    @ApiModelProperty(value = "批发价", required = true)
    private BigDecimal wholesalePrice;

    @ApiModelProperty(value = "结算价", required = true)
    private BigDecimal clearingPrice;
}
