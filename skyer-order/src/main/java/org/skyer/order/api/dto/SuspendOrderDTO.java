package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-02-23
 * @description:
 */
@Data
public class SuspendOrderDTO {
	@Encrypt
	private Long id;

	@ApiModelProperty(value = "挂起原因")
	private String suspendReason;
}
