package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: lzh
 * @date: 2022-04-22
 * @description:
 */
@Data
public class BatchStringListDTO {

	@ApiModelProperty(value = "字符串批量入参")
	private List<String> stringList;
}
