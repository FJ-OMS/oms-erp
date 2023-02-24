package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OutboundDeliveryOrderDetailDTO {

    @ApiModelProperty("主键")
    private Long id;
    @ApiModelProperty(value = "出库单ID")
    private Long odoId;
    @ApiModelProperty(value = "商品SKU编码")
    private String skuCode;
    @ApiModelProperty(value = "商品SKU名称")
    private String skuName;
    @ApiModelProperty(value = "商品编码")
    private String spuCode;
    @ApiModelProperty(value = "商品名称")
    private String spuName;
    @ApiModelProperty(value = "商品分类名称")
    private String categoryName;
    @ApiModelProperty(value = "计划数量")
    private BigDecimal plannedQty;
    @ApiModelProperty(value = "商品行")
    List<Long> orderGoodsLineIds;
}
