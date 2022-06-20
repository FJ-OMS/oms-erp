package org.skyer.channel.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: lzh
 * @date: 2021-06-18
 * @description:
 */
@ApiModel("渠道仓库列表请求参数")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WarehouseListDTO {

	@ApiModelProperty(value = "仓库名称")
	private String wareName;

	@ApiModelProperty("状态 true启用 false禁用")
	private Boolean status;


}
