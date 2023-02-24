package org.skyer.order.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import org.skyer.mybatis.domain.AuditDomain;

import java.math.BigDecimal;
import java.util.Date;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.starter.keyencrypt.core.Encrypt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 第三方售后单头
 *
 * @author lzh 2022-04-20 16:30:30
 */
@ApiModel("第三方售后单头")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_third_party_after_sale_header")
@Data
public class ThirdPartyAfterSaleHeader extends AuditDomain {

	public static final String FIELD_ID = "id";
	public static final String FIELD_OUT_REFUND_NO = "outRefundNo";
	public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
	public static final String FIELD_CHANNEL = "channel";
	public static final String FIELD_STORE_ID = "storeId";
	public static final String FIELD_STORE_CODE = "storeCode";
	public static final String FIELD_STORE_NAME = "storeName";
	public static final String FIELD_EXCEPTION_STATUS = "exceptionStatus";
	public static final String FIELD_EXCEPTION_REASON = "exceptionReason";
	public static final String FIELD_BUYER_REMARKS = "buyerRemarks";
	public static final String FIELD_GOODS_QUANTITY = "goodsQuantity";
	public static final String FIELD_TOTAL_AMOUNT = "totalAmount";
	public static final String FIELD_BUYER_NICK = "buyerNick";
	public static final String FIELD_PAY_TIME = "payTime";
	public static final String FIELD_OUT_RECEIVE_INFO = "outReceiveInfo";
	public static final String FIELD_OUT_RECEIVE_INFO_MD5 = "outReceiveInfoMd5";
	public static final String FIELD_STAGE = "stage";

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
	@ApiModelProperty(value = "第三方退货单号")
	private String outRefundNo;
	@ApiModelProperty(value = "三方订单号")
	private String outerOrderNo;
	@ApiModelProperty(value = "渠道")
	private String channel;
	@ApiModelProperty(value = "店铺id")
	private Long storeId;
	@ApiModelProperty(value = "店铺编码")
	private String storeCode;
	@ApiModelProperty(value = "店铺名称")
	private String storeName;
	@ApiModelProperty(value = "转单状态  0-  正常  1- 异常")
	private String exceptionStatus;
	@ApiModelProperty(value = "异常原因")
	private String exceptionReason;
	@ApiModelProperty(value = "买家备注/留言")
	private String remarks;
	@ApiModelProperty(value = "商品（行）总数量")
	private Long goodsQuantity;
	@ApiModelProperty(value = "总金额")
	private BigDecimal totalAmount;
	@ApiModelProperty(value = "买家昵称")
	private String buyerNick;
	@ApiModelProperty(value = "退款/退货时间")
	private Date refundTime;
	@ApiModelProperty(value = "拉单JSON信息")
	private String outReceiveInfo;
	@ApiModelProperty(value = "md5加密后json串")
	private String outReceiveInfoMd5;
	@ApiModelProperty(value = "退款/退货阶段   0-售中  1-售后")
	private String stage;
	@ApiModelProperty(value = "售后类型 1-退款单  2- 退货退款")
	private String type;
	@ApiModelProperty(value = "退货/退款原因")
	private String reason;
	@ApiModelProperty(value = "内部订单号")
	private String innerOrderNo;
	@ApiModelProperty(value = "内部退货单号")
	private String innerRefundNo;
}
