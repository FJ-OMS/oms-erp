package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-04-20
 * @description:
 */
@Data
public class ThirdPartyRefundBaseInfo {

	@ApiModelProperty(value = "第三方退货单号")
	@NotBlank(message = "第三方退货单号不能为空")
	private String outRefundNo;
	@ApiModelProperty(value = "内部订单号")
	private String innerOrderNo;
	@ApiModelProperty(value = "售后单类型 1-退款单  2- 退货退款")
	private String type;
	@ApiModelProperty(value = "三方订单号")
	@NotBlank(message = "第三方订单号不能为空")
	private String outerOrderNo;
	@ApiModelProperty(value = "渠道")
	private String channel;
	@ApiModelProperty(value = "店铺id")
	@Encrypt
	private Long storeId;
	@ApiModelProperty(value = "店铺编码")
	private String storeCode;
	@ApiModelProperty(value = "店铺名称")
	private String storeName;
	@ApiModelProperty(value = "转单状态  0-  正常  1- 异常")
	private String exceptionStatus;
	@ApiModelProperty(value = "异常原因")
	private String exceptionReason;
	@ApiModelProperty(value = "买家备注/留言/退货备注")
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
	@ApiModelProperty(value = "退货/退款原因")
	private String reason;

}
