package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-03-16
 * @description:
 */
@Data
public class AuditRefundDTO {

	@ApiModelProperty(value = "审核id")
	@Encrypt
	@NotNull(message = "审核id不能为空")
	private Long auditId;

	@ApiModelProperty(value = "status 1通过 0拒绝")
	private String status;

	@ApiModelProperty(value = "拒绝原因")
	private String failReason;

	@ApiModelProperty(value = "消息体")
	private String messageName;
}
