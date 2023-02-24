package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: lzh
 * @date: 2022-04-25
 * @description:
 */
@Data
public class WarehouseWarrantDetailDTO {

	@ApiModelProperty(value = "商品SKU编码")
	private String skuCode;

	@ApiModelProperty(value = "商品编码")
	private String spuCode;

	@ApiModelProperty(value = "sku名称")
	private String skuName;

	@ApiModelProperty(value = "商品名称")
	private String spuName;

	@ApiModelProperty(value = "计划数量")
	private BigDecimal plannedQty;
}
