package org.skyer.order.api.es;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

import org.skyer.order.api.dto.PageReqDTO;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-03-07
 * @description:
 */
@Data
public class SearchStockoutEsParam extends PageReqDTO {

	@ApiModelProperty(value = "第三方/内部订单号")
	private String orderNo;

	@ApiModelProperty(value = "SKU编码")
	private String skuCode;

	@ApiModelProperty(value = "SPU编码或者名称")
	private String spuName;

	@ApiModelProperty(value = "订单创建类型：普通转单、手工单、复制单、导入单")
	private String actionType;

	@ApiModelProperty(value = "渠道")
	private String channel;

	@ApiModelProperty(value = "店铺id")
	@Encrypt
	private List<Long> storeIdList;

	@ApiModelProperty(value = "订单交易类型：销售单、仅退款单、退货退款单、换货单")
	private String tradeType;

	@ApiModelProperty(value = "买家昵称")
	private String buyerNick;

	@ApiModelProperty(value = "收货人姓名")
	private String receiverName;

	@ApiModelProperty(value = "收货人手机号码")
	private String mobile;

	@ApiModelProperty(value = "买家备注/留言")
	private String buyerRemarks;

	@ApiModelProperty(value = "买家是否有备注")
	private Boolean isBuyerRemarks;

	@ApiModelProperty(value = "商家备注")
	private String orderRemarks;

	@ApiModelProperty(value = "是否有商家备注")
	private String isOrderRemarks;

	@ApiModelProperty(value = "有无赠品")
	private Boolean isGift;

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

	@ApiModelProperty(value = "预计发货起始时间")
	private Date predictDeliveryStartTime;

	@ApiModelProperty(value = "预计发货结束时间")
	private Date predictDeliveryEndTime;
}
