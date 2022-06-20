package org.skyer.channel.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2021-06-18
 * @description:
 */
@ApiModel("销售组织列表请求参数")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalesOrganizationListDTO {


	@ApiModelProperty(value = "组织名称")
	private String orgName;

	@ApiModelProperty(value = "营业执照名称")
	private String shopPermitName;

	@ApiModelProperty("状态 true启用 false禁用")
	private Boolean status;

	@ApiModelProperty(value = "所属上级公司")
	@Encrypt
	private Long pid;

}
