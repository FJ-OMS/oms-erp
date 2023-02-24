package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: lzh
 * @date: 2022-01-20
 * @description:
 */
@Data
public class ThirdOrderPageVo {

	@Encrypt
	private Long id;
	@ApiModelProperty(value = "平台订单号")
	private String outerOrderNo;
	@ApiModelProperty(value = "内部订单号")
	private String innerOrderNo;
	@ApiModelProperty(value = "流转状态")
	private String circulationStatus;
	@ApiModelProperty(value = "流转失败原因")
	private String failureReason;
	@ApiModelProperty(value = "回写状态 0：未回写 1：回写成功 2：回写失败")
	private String syncStatus;
	@ApiModelProperty(value = "平台收货状态 0：未收货 1：已收货")
	private String receivingStatus;
	@ApiModelProperty(value = "店铺id")
	@Encrypt
	private Long storeId;
	@ApiModelProperty(value = "店铺名称")
	private String storeName;
	@ApiModelProperty("渠道类型编码")
	@LovValue(lovCode = "SCHL.CHANNEL_TYPE", meaningField = "channelTypeMeaning")
	private String channel;
	@ApiModelProperty("渠道类型")
	private String channelTypeMeaning;
	@ApiModelProperty(value = "商品数量")
	private Long goodsQuantity;
	@ApiModelProperty(value = "总金额")
	private BigDecimal goodsTotalAmount;
	@ApiModelProperty(value = "优惠总金额")
	private BigDecimal totalDiscount;
	@ApiModelProperty(value = "买家实付")
	private BigDecimal buyerPayAmount;
	@ApiModelProperty(value = "应收总金额")
	private BigDecimal amountReceivable;
	@ApiModelProperty(value = "商家优惠金额")
	private BigDecimal businessDiscountAmount;
	@ApiModelProperty(value = "三方承担优惠金额")
	private BigDecimal thirdDiscountAmount;
	@ApiModelProperty(value = "买家昵称")
	private String buyerNick;
	@ApiModelProperty(value = "下单时间")
	private Date orderTime;
	@ApiModelProperty(value = "支付时间")
	private Date payTime;
	@ApiModelProperty(value = "内部单创建时间")
	private Date creationDate;

	@ApiModelProperty(value = "收货人姓名")
	private String receiverName;
	@ApiModelProperty(value = "收货人手机号码")
	private String mobile;
	@ApiModelProperty(value = "收货人详细地址")
	private String address;

	@ApiModelProperty(value = "商品总金额")
	private BigDecimal totalAmount;

	@ApiModelProperty(value = "买家应付")
	private BigDecimal buyerPay;

	@ApiModelProperty(value = "买家备注/留言")
	private String buyerRemarks;
	@ApiModelProperty(value = "客服备注")
	private String serviceRemarks;
	@ApiModelProperty(value = "商家/订单备注")
	private String orderRemarks;

	@ApiModelProperty(value = "订单类型")
	private String businessType;


	@ApiModelProperty(value = "订单状态")
	private String orderStatus;

	@ApiModelProperty(value = "买家实付")
	private BigDecimal buyerRealPrice;

	@ApiModelProperty(value = "支付状态")
	private Integer payStatus;


}
