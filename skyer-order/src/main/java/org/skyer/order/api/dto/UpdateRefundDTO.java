package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-03-16
 * @description:
 */
@Data
public class UpdateRefundDTO {
	@Encrypt
	private Long id;
	@ApiModelProperty(value = "退款类型/退货退款类型：1-未发货仅退款  1-退货退款")
	private String refundType;
	@ApiModelProperty(value = "退款/退货原因")
	private String reason;
	@ApiModelProperty(value = "退款/退货阶段   0-售中  1-售后")
	private String stage;
	@ApiModelProperty(value = "买家备注/留言")
	private String buyerRemarks;
	@ApiModelProperty(value = "客服备注")
	private String serviceRemarks;

	@ApiModelProperty(value = "附件(可多个逗号隔开）")
	private String accessory;

	@ApiModelProperty(value = "标签code 多个,分割")
	private String tags;

	@ApiModelProperty(value = "退货物流信息")
	private List<RefundLogisticsDTO.Logistics> logisticsList;

	@ApiModelProperty(value = "收货仓库")
	private Long receivingWarehouse;
	@ApiModelProperty(value = "收货仓库名称")
	private String receivingWarehouseName;
	@ApiModelProperty(value = "仓库收货状态")
	private String receivingWarehouseStatus;

	@ApiModelProperty(value = "状态")
	private String status;
}
