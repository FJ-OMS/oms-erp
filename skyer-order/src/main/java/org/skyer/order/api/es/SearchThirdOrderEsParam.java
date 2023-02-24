package org.skyer.order.api.es;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.order.api.dto.PageReqDTO;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.Date;
import java.util.List;

/**
 * Es搜索入参
 * @author : zlb
 */
@Data
public class SearchThirdOrderEsParam extends PageReqDTO {

	@ApiModelProperty(value = "第三方/内部订单号")
	private String orderNo;

	@ApiModelProperty(value = "渠道")
	private String channel;

	@ApiModelProperty(value = "店铺id")
	@Encrypt
	private List<Long> storeIdList;

	@ApiModelProperty(value = "买家昵称")
	private String buyerNick;

	@ApiModelProperty(value = "回写状态")
	private String syncStatus;

	@ApiModelProperty(value = "收货人姓名")
	private String receiverName;

	@ApiModelProperty(value = "收货人手机号码")
	private String mobile;

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

	@ApiModelProperty(value = "订单类型，如：手工销售订单、直营销售订单、菜鸟外仓销售订单、京东外仓销售订单、直营退货退款订单、平台O2O销售订单、平台预售定金单等")
	private String businessType;



}
