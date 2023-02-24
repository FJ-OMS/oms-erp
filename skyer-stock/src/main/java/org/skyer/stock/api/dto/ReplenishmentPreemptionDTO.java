package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReplenishmentPreemptionDTO {

    @ApiModelProperty(value = "虚仓ID")
    private Long virtualWarehouseId;
    @ApiModelProperty(value = "预占虚仓名称")
    private String virtualWarehouseName;
    @ApiModelProperty(value = "渠道仓ID")
    private Long channelWarehouseId;
    @ApiModelProperty(value = "商品明细行ID")
    private Long orderGoodsLineId;
}
