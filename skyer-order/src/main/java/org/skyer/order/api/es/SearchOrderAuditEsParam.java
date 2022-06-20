package org.skyer.order.api.es;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.order.api.dto.PageReqDTO;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.Date;
import java.util.List;

/**
 * 订单审核入参
 * @author: zlb
 * @date: 2022-01-21
 * @description:
 */
@Data
public class SearchOrderAuditEsParam extends PageReqDTO {

	@ApiModelProperty(value = "第三方/内部订单号")
	private String orderNo;

	@ApiModelProperty(value = "渠道")
	private String channel;

	@ApiModelProperty(value = "审核状态")
	private List<String> auditStatus;

	@ApiModelProperty(value = "审核类型 0寻源前待审核状态  1发货前待审核状态")
	private Integer auditType;

	@ApiModelProperty(value = "店铺id")
	@Encrypt
	private List<Long> storeIdList;

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


	@ApiModelProperty(value = "聚合商品行的预占状态")
	private String preemptionStatus;


	@ApiModelProperty(value = "三方下单起始时间")
	private Date orderStartTime;

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

	@ApiModelProperty(value = "SKU编码")
	private String skuCode;

	@ApiModelProperty(value = "订单类型，如：手工销售订单、直营销售订单、菜鸟外仓销售订单、京东外仓销售订单、直营退货退款订单、平台O2O销售订单、平台预售定金单等")
	private String businessType;

	@ApiModelProperty(value = "SPU编码或者名称")
	private String spuName;

	@ApiModelProperty(value = "商家备注")
	private String orderRemarks;

	@ApiModelProperty(value = "是否有商家备注")
	private String isOrderRemarks;
}
