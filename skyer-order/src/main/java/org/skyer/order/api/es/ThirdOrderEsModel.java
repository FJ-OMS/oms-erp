package org.skyer.order.api.es;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : zlb
 */
@Data
public class ThirdOrderEsModel {

	private Long id;

	/**
	 * 平台订单号
	 */
	private String outerOrderNo;
	/**
	 * 内部订单号
	 */
	private String innerOrderNo;
	/**
	 * 店铺id
	 */
	private Long storeId;
	/**
	 * 店铺名称
	 */
	private String storeName;
	/**
	 * 渠道类型
	 */
	private String channel;

	@ApiModelProperty(value = "流转状态")
	private String circulationStatus;
	@ApiModelProperty(value = "流转失败原因")
	private String failureReason;
	@ApiModelProperty(value = "回写状态 0：未回写 1：回写成功 2：回写失败")
	private String syncStatus;
	@ApiModelProperty(value = "平台收货状态 0：未收货 1：已收货")
	private String receivingStatus;
	/**
	 * 商品数量
	 */
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
	/**
	 * 买家昵称
	 */
	private String buyerNick;

	/**
	 * 三方下单时间
	 */
	private Date orderTime;
	/**
	 * 支付时间
	 */
	private Date payTime;

	@ApiModelProperty(value = "收货人姓名")
	private String receiverName;
	@ApiModelProperty(value = "收货人手机号码")
	private String mobile;
	@ApiModelProperty(value = "收货人详细地址")
	private String address;

	/**
	 * 订单类型
	 */
	private String businessType;

	@ApiModelProperty(value = "买家备注/留言")
	private String buyerRemarks;
	@ApiModelProperty(value = "客服备注")
	private String serviceRemarks;
	@ApiModelProperty(value = "商家/订单备注")
	private String orderRemarks;
	/**
	 * 内部单创建时间
	 */
	private Date creationDate;

	private Long createdBy;
	private Date lastUpdateDate;
	private Long lastUpdatedBy;


}
