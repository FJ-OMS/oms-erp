package org.skyer.order.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: lzh
 * @date: 2022-03-16
 * @description:
 */
@Data
public class RefundInfoDTO {

	@ApiModelProperty(value = "第三方退款/退货单号")
	private String outerRefundNo;

	@ApiModelProperty(value = "三方退款/退回退款时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date outReturnTime;

	@ApiModelProperty(value = "退款类型/退货退款类型：1-未发货仅退款  1-退货退款")
	private String refundType;

	@ApiModelProperty(value = "退款单类型： 普通退款单 ")
	private String orderRefundType;

	@ApiModelProperty(value = "退款/退货原因")
	private String reason;

	@ApiModelProperty(value = "状态")
	private String status;

	@ApiModelProperty(value = "退款/退货阶段   0-售中  1-售后")
	private String stage;

	@ApiModelProperty(value = "结算方式")
	private String payType;
	@ApiModelProperty(value = "附件(可多个逗号隔开）")
	private String accessory;
	@ApiModelProperty(value = "买家备注/留言")
	private String buyerRemarks;
	@ApiModelProperty(value = "客服备注")
	private String serviceRemarks;
}
