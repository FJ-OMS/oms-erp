package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-03-23
 * @description:
 */
@Data
public class RefundItemLineVO {

	@ApiModelProperty("主键id")
	@Encrypt
	private Long id;
	@ApiModelProperty(value = "第三方退款/退货单号")
	private String outerRefundNo;
	@ApiModelProperty(value = "内部退款/退货单号")
	private String innerRefundNo;
	@ApiModelProperty(value = "内部订单号")
	private String innerOrderNo;
	@ApiModelProperty(value = "原itemLineId")
	@Encrypt
	private Long originItemLineId;
	@ApiModelProperty(value = "原itemId")
	@Encrypt
	private Long originItemId;
	@ApiModelProperty(value = "内部退款单商品ID")
	@Encrypt
	private Long innerRefundItemId;
	@ApiModelProperty(value = "售后类型 1-退款单  2- 退货退款")
	private String type;
	@ApiModelProperty(value = "商品编码")
	private String spuCode;
	@ApiModelProperty(value = "skuId")
	private Long skuId;
	@ApiModelProperty(value = "商品类型1.实物2虚拟")
	private Integer spuType;
	@ApiModelProperty(value = "SKU编码")
	private String skuCode;
	@ApiModelProperty(value = "SKU名称")
	private String skuName;
	@ApiModelProperty(value = "spu名称")
	private String spuName;
	@ApiModelProperty(value = "退款单价(均摊商品行单价)")
	private BigDecimal unitPrice;

	@ApiModelProperty(value = "数量")
	private Integer quantity;

	@ApiModelProperty(value = "可退货数量")
	private Integer returnableQuantity;
	@ApiModelProperty(value = "退货数量")
	private Integer returnQuantity;
	@ApiModelProperty(value = "退款总价")
	private BigDecimal totalPrice;

}
