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
@ApiModel("经销商列表请求")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DealerListDTO {

	@ApiModelProperty(value = "经销商名称")
	private String dealerName;

	@ApiModelProperty("状态 true启用 false禁用")
	private Boolean status;


}
