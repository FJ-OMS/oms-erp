package org.skyer.order.api.es;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.order.api.vo.OrderItemLineVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: lzh
 * @date: 2022-01-21
 * @description:
 */
@Data
public class OrderEsModel  {

	private Long id;
	/**
	 * 订单状态
	 */
	private String orderStatus;
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

	/**
	 * 订单创建类型：普通转单、手工单、复制单、导入单
	 */
	private String actionType;

	/**
	 * 订单交易类型：销售单、仅退款单、退货退款单、换货单
	 */
	private String tradeType;
	/**
	 * 商品数量
	 */
	private Long goodsQuatity;
	/**
	 * 买家昵称
	 */
	private String buyerNick;
	/**
	 * 有无赠品
	 */
	private Boolean isGift;

	/**
	 * 买家是否有备注
	 */
	private Boolean isBuyerRemarks;

	/**
	 *  买家备注
	 */
	private String buyerRemarks;

	/**
	 * 商家备注
	 */
	private String orderRemarks;

	/**
	 * 客服是否有备注
	 */
	private Boolean isServiceRemarks;

	/**
	 *  客服备注
	 */
	private String serviceRemarks;

	/**
	 * 标签
	 */
	private List<String> tagsList;

	/**
	 * 三方下单时间
	 */
	private Date orderTime;
	/**
	 * 支付时间
	 */
	private Date payTime;
	/**
	 * 内部单创建时间
	 */
	private Date creationDate;

	/**
	 * 预计发货时间
	 */
	private Date predictDeliveryTime;

	/**
	 *  发货时间
	 */
	private Date deliveryTime;

	/**
	 * 确认收货时间
	 */
	private Date receivedTime;


	/**
	 * 收货人
	 */
	private String receiverName;
	/**
	 * 收货人手机号码
	 */
	private String mobile;
	/**
	 * 收货人详细地址
	 */
	private String address;

	/**
	 * 商品行相关信息
	 */
	private List<OrderItemLineVO> orderItemLineList;


	/**
	 * 商品总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 总成本
	 */
	private BigDecimal costPrice;

	/**
	 * 买家应付
	 */
	private BigDecimal buyerPay;

	/**
	 * 聚合商品行的预占状态
	 */
	private String preemptionStatus;

	/**
	 * 聚合商品行的寻源状态
	 */
	private String sourceStatus;

	/**
	 * 聚合商品行的发（收）货状态
	 */
	private String deliveryStatus;

	/**
	 * 订单级-挂起状态：0-正常，1-挂起成功，2-挂起失败，3-解挂成功，4-解挂失败
	 */
	private Integer suspendStatus;

	/**
	 * 聚合商品行，预占后渠道仓数量同步平台状态
	 */
	private String syncPreempStatus;

	/**
	 * 聚合商品行，发（收）货同步平台状态
	 */
	private String syncDeliveryStatus;

	/**
	 * 是否缺货
	 */
	private Boolean stockFlag;



	/**
	 * 订单类型
	 */
	private String businessType;

	/**
	 * 商家是否有备注
	 */
	private Boolean isOrderRemarks;


	private Long createdBy;
	private Date lastUpdateDate;
	private Long lastUpdatedBy;

	/**
	 * 审核状态
	 */
	private String endorseStatus;

	/**
	 * 审核状态
	 */
	private String refundStatus;

	@ApiModelProperty(value = "买家实付")
	private BigDecimal buyerRealPrice;

	@ApiModelProperty(value = "支付状态")
	private Integer payStatus;

	@ApiModelProperty(value = "平台收货状态 0：未收货 1：已收货")
	private String receivingStatus;

	@ApiModelProperty(value = "提货店铺Id")
	private Long takingDeliveryStoreId;

	@ApiModelProperty(value = "提货店铺名称")
	private String takingDeliveryStoreName;

	@ApiModelProperty(value = "销售员工")
	private String salesEmployeeRealName;

	@ApiModelProperty(value = "提货方式")
	private String takingDeliveryWay;
}
