package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-03-16
 * @description:
 */
@Data
public class OrderRefundInfoDTO {
	@Encrypt
	@ApiModelProperty(value = "关联订单号ID")
	private Long originOrderId;
	@ApiModelProperty(value = "平台订单号")
	private String outerOrderNo;
	@ApiModelProperty(value = "内部订单号")
	private String innerOrderNo;
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
	@ApiModelProperty(value = "订单创建类型：普通转单、手工单、复制单、导入单")
	private String actionType;
	@ApiModelProperty(value = "商品数量")
	private Long goodsQuantity;
	@ApiModelProperty(value = "买家昵称")
	private String buyerNick;
	@ApiModelProperty(value = "下单时间")
	private Date orderTime;
	@ApiModelProperty(value = "支付时间")
	private Date payTime;
	@ApiModelProperty(value = "内部单创建时间")
	private Date creationDate;

	@ApiModelProperty(value = "退款单类型： 普通退款单 ")
	private String orderRefundType;

	@ApiModelProperty(value = "收货人姓名")
	private String receiverName;
	@ApiModelProperty(value = "收货人手机号码")
	private String mobile;
	@ApiModelProperty(value = "收货人详细地址")
	private String address;

	@ApiModelProperty(value = "物流单号")
	private List<String> logisticsNo;

	@ApiModelProperty(value = "商品总金额")
	private BigDecimal totalAmount;

	@ApiModelProperty(value = "买家应付")
	private BigDecimal buyerPay;
	@ApiModelProperty(value = "商品总成本")
	private BigDecimal costPrice;
	@ApiModelProperty(value = "预计发货时间")
	private Date predictDeliveryTime;

	@ApiModelProperty(value = "预占状态")
	private String preemptionStatus;
	@ApiModelProperty(value = "寻源状态")
	private String sourceStatus;

	@ApiModelProperty(value = "订单级-挂起状态")
	private Integer suspendStatus;

	@ApiModelProperty(value = "发货状态")
	private String deliveryStatus;

	@ApiModelProperty(value = "预占回写")
	private String syncPreempStatus;

	@ApiModelProperty(value = "发货回写")
	private String syncDeliveryStatus;

	@ApiModelProperty(value = "是否缺货")
	private Boolean stockFlag;

	@ApiModelProperty(value = "有无赠品")
	private Boolean isGift;

	@ApiModelProperty(value = "退款状态")
	private Integer refundStatus;

	@ApiModelProperty(value = "订单备注")
	private String orderRemarks;

	@ApiModelProperty(value = "买家备注/留言")
	private String buyerRemarks;

	@ApiModelProperty(value = "客服备注")
	private String serviceRemarks;
	@ApiModelProperty(value = "订单类型")
	private String businessType;
	@ApiModelProperty(value = "标签")
	private List<String> tagsList;

	@ApiModelProperty(value = "订单状态")
	private String orderStatus;
	@ApiModelProperty(value = "店铺编码")
	private String storeCode;
}
