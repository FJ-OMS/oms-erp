package org.skyer.order.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import org.skyer.mybatis.domain.AuditDomain;

import java.math.BigDecimal;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 第三方售后单商品
 *
 * @author lzh 2022-04-20 16:30:29
 */
@ApiModel("第三方售后单商品")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_third_party_after_sale_item")
@Data
public class ThirdPartyAfterSaleItem extends AuditDomain {

	public static final String FIELD_ID = "id";
	public static final String FIELD_OUT_REFUND_NO = "outRefundNo";
	public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_SPU_CODE = "spuCode";
	public static final String FIELD_SKU_CODE = "skuCode";
	public static final String FIELD_SKU_NAME = "skuName";
	public static final String FIELD_SPU_NAME = "spuName";
	public static final String FIELD_OUTER_SALE_PRICE = "outerSalePrice";
	public static final String FIELD_QUANTITY = "quantity";
	public static final String FIELD_TOTAL_AMOUNT = "totalAmount";

	//
	// 业务方法(按public protected private顺序排列)
	// ------------------------------------------------------------------------------

	//
	// 数据库字段
	// ------------------------------------------------------------------------------


	@ApiModelProperty("主键id")
	@Id
	@GeneratedValue
	private Long id;
	@ApiModelProperty(value = "第三方退货单号")
	private String outRefundNo;
	@ApiModelProperty(value = "三方订单号")
	private String outerOrderNo;
	@ApiModelProperty(value = "售后类型 1-三方退款单  2- 三方退货退款单")
	private String type;
	@ApiModelProperty(value = "商品编码")
	private String spuCode;
	@ApiModelProperty(value = "SKU编码")
	private String skuCode;
	@ApiModelProperty(value = "SKU名称")
	private String skuName;
	@ApiModelProperty(value = "spu名称")
	private String spuName;
	@ApiModelProperty(value = "三方销售单价")
	private BigDecimal outerSalePrice;
	@ApiModelProperty(value = "数量")
	private Long quantity;
	@ApiModelProperty(value = "退款总金额")
	private BigDecimal totalAmount;
	@ApiModelProperty(value = "skuId")
	private Long skuId;
	@ApiModelProperty(value = "商品类型1.实物2虚拟")
	private Integer spuType;
	@ApiModelProperty(value = "赠品标识：0-卖品，1-赠品")
	private Integer giftFlag;
	@ApiModelProperty(value = "内部订单号")
	private String innerOrderNo;
}
