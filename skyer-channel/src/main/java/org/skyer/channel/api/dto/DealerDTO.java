package org.skyer.channel.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2021-06-17
 * @description:
 */
@Data
@ApiModel("经销商传参")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DealerDTO {

	@ApiModelProperty("表ID，主键，供其他表做外键")
	@Encrypt
	private Long id;

	@ApiModelProperty(value = "经销商编码")
	@NotNull
	private String dealerCode;

	@ApiModelProperty(value = "经销商名称")
	@NotNull
	private String dealerName;

	@ApiModelProperty(value = "联系人姓名")
	@NotNull
	private String contactName;

	@ApiModelProperty(value = "联系人电话")
	@NotNull
	private String contactPhone;

	@ApiModelProperty(value = "描述")
	private String description;

}
