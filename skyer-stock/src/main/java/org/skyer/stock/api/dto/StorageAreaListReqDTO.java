package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lzh
 * @date: 2021-08-20
 * @description:
 */
@Data
public class StorageAreaListReqDTO {

	@ApiModelProperty(value = "仓储名称")
	private String name;

	@ApiModelProperty(value = "仓储编码")
	private String code;

	@ApiModelProperty("可用状态：false禁用，true启用")
	private Boolean enableFlag;
}
