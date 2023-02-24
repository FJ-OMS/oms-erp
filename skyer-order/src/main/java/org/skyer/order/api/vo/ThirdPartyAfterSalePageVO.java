package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-04-21
 * @description:
 */
@Data
public class ThirdPartyAfterSalePageVO {
	@Encrypt
	private Long id;
	@ApiModelProperty(value = "第三方退货单号")
	private String outRefundNo;
	@ApiModelProperty(value = "三方订单号")
	private String outerOrderNo;
	@ApiModelProperty("渠道类型编码")
	@LovValue(lovCode = "SCHL.CHANNEL_TYPE", meaningField = "channelTypeMeaning")
	private String channel;
	@ApiModelProperty("渠道类型")
	private String channelTypeMeaning;
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
