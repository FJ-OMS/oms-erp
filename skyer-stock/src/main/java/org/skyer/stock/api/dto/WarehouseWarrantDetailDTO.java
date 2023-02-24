package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WarehouseWarrantDetailDTO {

    @ApiModelProperty(value = "商品SKU编码")
    private String skuCode;

    @ApiModelProperty(value = "sku名称")
    private String skuName;

    @ApiModelProperty(value = "商品编码")
    private String spuCode;

    @ApiModelProperty(value = "商品名称")
    private String spuName;

    @ApiModelProperty(value = "计划数量")
    private BigDecimal plannedQty;
}
