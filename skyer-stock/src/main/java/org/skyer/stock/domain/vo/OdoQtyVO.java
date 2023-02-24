package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xurongfa 2022-02-22 10:55
 */
@Data
@ApiModel(value = "通知单发货数量")
public class OdoQtyVO {
    @ApiModelProperty(value = "计划发货数量")
    private BigDecimal plannedQty;

    @ApiModelProperty(value = "已发货数量")
    private BigDecimal alreadyOutQty;
}
