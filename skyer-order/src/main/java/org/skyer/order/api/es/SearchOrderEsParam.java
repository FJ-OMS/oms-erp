package org.skyer.order.api.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.skyer.order.api.dto.PageReqDTO;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * Es搜索入参
 * @author: lzh
 * @date: 2022-01-21
 * @description:
 */
@Data
public class  SearchOrderEsParam extends PageReqDTO {

	@ApiModelProperty(value = "第三方/内部订单号")
	private String orderNo;

	@ApiModelProperty(value = "订单类型，如：平台O2O销售订单、手工销售订单、直营销售订单、菜鸟外仓销售订单、京东外仓销售订单、直营退货退款订单、、平台预售定金单等")
	private String businessType;

	@ApiModelProperty(value = "渠道")
	private String channel;

	@ApiModelProperty(value = "店铺id")
	@Encrypt
	private List<Long> storeIdList;

	@ApiModelProperty(value = "订单交易类型：销售单、仅退款单、退货退款单、换货单")
	private String tradeType;

	@ApiModelProperty(value = "订单创建类型：普通转单、手工单、复制单、导入单")
	private String actionType;

	@ApiModelProperty(value = "有无赠品")
	private Boolean isGift;

	@ApiModelProperty(value = "买家昵称")
	private String buyerNick;

	@ApiModelProperty(value = "收货人姓名")
	private String receiverName;

	@ApiModelProperty(value = "收货人手机号码")
	private String mobile;

	@ApiModelProperty(value = "买家备注/留言")
	private String buyerRemarks;

	@ApiModelProperty(value = "客服备注")
	private String serviceRemarks;

	@ApiModelProperty(value = "买家是否有备注")
	private Boolean isBuyerRemarks;

	@ApiModelProperty(value = "商家是否有备注")
	private Boolean isServiceRemarks;

	@ApiModelProperty(value = "标签")
	private List<String> tags;

	@ApiModelProperty(value = "聚合商品行的预占状态")
	private String preemptionStatus;

	@ApiModelProperty(value = "聚合商品行的寻源状态")
	private String sourceStatus;

	@ApiModelProperty(value = "聚合商品行的发（收）货状态")
	private String deliveryStatus;

	@ApiModelProperty(value = "订单级-挂起状态：0-正常，1-挂起成功，2-挂起失败，3-解挂成功，4-解挂失败")
	private Integer suspendStatus;

	@ApiModelProperty(value = "是否缺货")
	private Boolean stockFlag;

	@ApiModelProperty(value = "三方下单起始时间")
	private Date orderStartTime;

	@ApiModelProperty(value = "订单状态")
	private String orderStatus;

	@ApiModelProperty(value = "三方下单结束时间")
	private Date orderEndTime;

	@ApiModelProperty(value = "支付起始时间")
	private Date payStartTime;

	@ApiModelProperty(value = "支付结束时间")
	private Date payEndTime;

	@ApiModelProperty(value = "内部单创建起始时间")
	private Date creationStartDate;

	@ApiModelProperty(value = "内部单创建结束时间")
	private Date creationEndDate;

	@ApiModelProperty(value = "预计发货起始时间")
	private Date predictDeliveryStartTime;

	@ApiModelProperty(value = "预计发货结束时间")
	private Date predictDeliveryEndTime;

	@ApiModelProperty(value = "发货起始时间")
	private Date deliveryStartTime;

	@ApiModelProperty(value = "发货结束时间")
	private Date deliveryEndTime;

	@ApiModelProperty(value = "确认收货起始时间")
	private Date receivedStartTime;

	@ApiModelProperty(value = "确认收货结束时间")
	private Date receivedEndTime;

	@ApiModelProperty(value = "商品总金额最小金额")
	private Double totalAmountMin;

	@ApiModelProperty(value = "商品总金额最大金额")
	private Double totalAmountMax;

	@ApiModelProperty(value = "总成本最小金额")
	private Double costPriceMin;

	@ApiModelProperty(value = "总成本最大金额")
	private Double costPriceMax;

	@ApiModelProperty(value = "买家应付最小金额")
	private Double buyerPayMin;

	@ApiModelProperty(value = "买家应付最大金额")
	private Double buyerPayMax;

	@ApiModelProperty(value = "物流单号")
	private String expressNo;

	@ApiModelProperty(value = "SKU编码")
	private String skuCode;

	@ApiModelProperty(value = "SPU编码或者名称")
	private String spuName;

	@ApiModelProperty(value = "预占回写状态")
	private String syncPreempStatus;

	@ApiModelProperty(value = "发货回写状态")
	private String syncDeliveryStatus;

	@ApiModelProperty(value = "商家备注")
	private String orderRemarks;

	@ApiModelProperty(value = "是否有商家备注")
	private String isOrderRemarks;

	@ApiModelProperty(value = "收货状态")
	private String receiveStatus;

	@ApiModelProperty(value = "发货状态多选")
	private List<String> deliveryStatusList;

}
