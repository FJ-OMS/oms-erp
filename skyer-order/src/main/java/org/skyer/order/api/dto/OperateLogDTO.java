package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: lzh
 * @date: 2022-02-28
 * @description:
 */
@Data
public class OperateLogDTO {

	@ApiModelProperty(value = "平台订单号", required = true)
	@NotBlank
	private String outerOrderNo;
	@ApiModelProperty(value = "内部订单号", required = true)
	@NotBlank
	private String innerOrderNo;
	@ApiModelProperty(value = "当前节点", required = true)
	private String node;
	@ApiModelProperty(value = "动作", required = true)
	private String action;
	@ApiModelProperty(value = "修改前的值")
	private String origValue;
	@ApiModelProperty(value = "修改后的值")
	private String curValue;
	@ApiModelProperty(value = "修改内容")
	private String message;
}
