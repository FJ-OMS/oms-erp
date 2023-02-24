package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lzh
 * @date: 2022-01-17
 * @description:
 */
@Data
public class ProcessDefinePageDTO {

	@ApiModelProperty(value = "流程名称")
	private String processName;
}
