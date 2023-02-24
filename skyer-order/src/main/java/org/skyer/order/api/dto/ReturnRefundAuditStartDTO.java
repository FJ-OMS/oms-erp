package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lzh
 * @date: 2022-03-24
 * @description:
 */
@Data
public class ReturnRefundAuditStartDTO {
	@ApiModelProperty(value = "审核单id")
	private Long auditId;

	@ApiModelProperty(value = "审核结果")
	private Boolean flag;

	@ApiModelProperty(value = "退货退款状态")
	private String returnStatus;

	@ApiModelProperty(value = "是否最终审核")
	private Integer isAuditEnd;

	@ApiModelProperty(value = "拒绝原因")
	private String failReason;

	@ApiModelProperty(value = "是否为仅退款")
	private Boolean onlyRefundFlag;
}
