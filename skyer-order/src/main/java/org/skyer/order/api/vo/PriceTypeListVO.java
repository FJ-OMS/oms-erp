package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lzh
 * @date: 2022-04-12
 * @description:
 */
@Data
public class PriceTypeListVO {


	@ApiModelProperty(value = "类型编码")
	private String value;

	@ApiModelProperty(value = "类型名称")
	private String label;
}
