package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-02-21
 * @description:
 */
@Data
public class CopyOrderItemVO {


	@Encrypt
	@ApiModelProperty("skuId")
	@NotNull(message = "skuId不能为空")
	private Long skuId;

	@ApiModelProperty("sku编码")
	@NotBlank(message = "sku编码不能为空")
	private String skuCode;
	@ApiModelProperty("数量")
	private Integer quantity;

	@ApiModelProperty("外部销售总价")
	private BigDecimal outerSaleAmount;

	@ApiModelProperty("外部销售单价")
	private BigDecimal outerSingleSaleAmount;

	@ApiModelProperty("内部销售总价")
	private BigDecimal innerSaleAmount;

	@ApiModelProperty("内部销售单价")
	private BigDecimal innerSingleSaleAmount;

	@ApiModelProperty("外部均摊价格")
	private BigDecimal outBalancePrice;

	@ApiModelProperty("内部均摊价格")
	private BigDecimal innerBalancePrice;

	@ApiModelProperty("是否为套餐")
	private Integer setMealFlag;

	@ApiModelProperty("1赠品 2随品")
	private Integer giftFlag;

	@ApiModelProperty("运费")
	private BigDecimal transPrice;

	@ApiModelProperty(value = "成本价，计价单位：元，保留小数点后2位")
	private BigDecimal costPrice;
	@ApiModelProperty(value = "spu名称")
	private String spuName;
	@ApiModelProperty(value = "SKU名称")
	private String skuName;
	@ApiModelProperty(value = "商品编码")
	private String spuCode;
}
