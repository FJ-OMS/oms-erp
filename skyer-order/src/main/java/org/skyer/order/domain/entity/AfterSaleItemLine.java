package org.skyer.order.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import lombok.Data;

import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.starter.keyencrypt.core.Encrypt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 售后单商品行
 *
 * @author lzh 2022-03-16 10:55:40
 */
@ApiModel("售后单商品行")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_after_sale_item_line")
@Data
public class AfterSaleItemLine extends AuditDomain {

	public static final String FIELD_ID = "id";
	public static final String FIELD_OUTER_REFUND_NO = "outerRefundNo";
	public static final String FIELD_INNER_REFUND_NO = "innerRefundNo";
	public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
	public static final String ORIGIN_ITEM_ID = "originItemId";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_SPU_CODE = "spuCode";
	public static final String FIELD_SKU_ID = "skuId";
	public static final String FIELD_SPU_TYPE = "spuType";
	public static final String FIELD_SKU_CODE = "skuCode";
	public static final String FIELD_SKU_NAME = "skuName";
	public static final String FIELD_SPU_NAME = "spuName";
	public static final String FIELD_DELETE_FLAG = "deleteFlag";

	//
	// 业务方法(按public protected private顺序排列)
	// ------------------------------------------------------------------------------

	//
	// 数据库字段
	// ------------------------------------------------------------------------------


	@ApiModelProperty("主键id")
	@Id
	@GeneratedValue
	@Encrypt
	private Long id;
	@ApiModelProperty(value = "第三方退款/退货单号")
	private String outerRefundNo;
	@ApiModelProperty(value = "内部退款/退货单号")
	private String innerRefundNo;
	@ApiModelProperty(value = "内部订单号")
	private String innerOrderNo;
	@ApiModelProperty(value = "外部订单号")
	private String outerOrderNo;
	@ApiModelProperty(value = "原itemLineId")
	@Encrypt
	private Long originItemLineId;
	@ApiModelProperty(value = "原itemId")
	@Encrypt
	private Long originItemId;
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
	@ApiModelProperty(value = "赠品标识：0-卖品，1-赠品")
	private Integer giftFlag;
	@ApiModelProperty(value = "退款单价(均摊商品行单价)")
	private BigDecimal unitPrice;
	@ApiModelProperty(value = "删除标识")
	private Integer deleteFlag;

	//
	// 非数据库字段
	// ------------------------------------------------------------------------------

	//
	// getter/setter
	// ------------------------------------------------------------------------------


}
