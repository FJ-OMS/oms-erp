package org.skyer.order.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import lombok.Data;

import org.skyer.mybatis.domain.AuditDomain;

import java.math.BigDecimal;
import java.util.List;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * 售后单商品
 *
 * @author lzh 2022-03-16 10:55:40
 */
@ApiModel("售后单商品")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_after_sale_item")
@Data
public class AfterSaleItem extends AuditDomain {

	public static final String FIELD_ID = "id";
	public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
	public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_ORIGIN_ITEM_ID = "originItemId";
	public static final String FIELD_OUTER_REFUND_NO = "outerRefundNo";
	public static final String FIELD_INNER_REFUND_NO = "innerRefundNo";
	public static final String FIELD_SPU_CODE = "spuCode";
	public static final String FIELD_SPU_TYPE = "spuType";
	public static final String FIELD_SKU_CODE = "skuCode";
	public static final String FIELD_SKU_NAME = "skuName";
	public static final String FIELD_SPU_NAME = "spuName";
	public static final String FIELD_SKU_ID = "skuId";
	public static final String FIELD_QUANTITY = "quantity";
	public static final String FIELD_UNIT_PRICE = "unitPrice";
	public static final String FIELD_GIFT_FLAG = "giftFlag";
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
	@ApiModelProperty(value = "平台订单号")
	private String outerOrderNo;
	@ApiModelProperty(value = "内部订单号")
	private String innerOrderNo;
	@ApiModelProperty(value = "售后类型 1-退款单  2- 退货退款")
	private String type;
	@ApiModelProperty(value = "原itemId")
	@Encrypt
	private Long originItemId;
	@ApiModelProperty(value = "第三方退款/退货单号")
	private String outerRefundNo;
	@ApiModelProperty(value = "内部退款/退货单号")
	private String innerRefundNo;
	@ApiModelProperty(value = "商品编码")
	private String spuCode;
	@ApiModelProperty(value = "商品类型1.实物2虚拟")
	private Integer spuType;
	@ApiModelProperty(value = "SKU编码")
	private String skuCode;
	@ApiModelProperty(value = "SKU名称")
	private String skuName;
	@ApiModelProperty(value = "spu名称")
	private String spuName;
	@ApiModelProperty(value = "skuId")
	private Long skuId;
	@ApiModelProperty(value = "退款数量")
	private Long quantity;
	@ApiModelProperty(value = "退款单价(均摊商品行单价)")
	private BigDecimal unitPrice;
	@ApiModelProperty(value = "赠品标识：0-卖品，1-赠品")
	private Integer giftFlag;
	@ApiModelProperty(value = "删除标识")
	private Integer deleteFlag;


	//
	// 非数据库字段
	// ------------------------------------------------------------------------------


	@ApiModelProperty(value = "商品行数量")
	@Transient
	private Integer totalQuantity;
	@ApiModelProperty(value = "可退货数量")
	@Transient
	private Integer returnableQuantity;
	@ApiModelProperty(value = "已退款数量")
	@Transient
	private Long quantityRefunded;
	@ApiModelProperty(value = "未发货数量")
	@Transient
	private Long undeliveredQuantity;
	@ApiModelProperty(value = "可退款数量")
	@Transient
	private Long refundableQuantity;
	@ApiModelProperty(value = "已发货数量")
	@Transient
	private Long quantityShipped;
	@ApiModelProperty(value = "已退货数量")
	@Transient
	private Long quantityReturned;

	@ApiModelProperty(value = "商品行")
	@Transient
	private List<AfterSaleItemLine> itemLines;
	//
	// getter/setter
	// ------------------------------------------------------------------------------


}
