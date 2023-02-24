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
 * @date: 2021-06-21
 * @description:
 */
@ApiModel("仓库修改请求参数")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WarehouseDTO {

	@ApiModelProperty("表ID，主键，供其他表做外键")
	@Id
	@GeneratedValue
	@Encrypt
	private Long id;

	@ApiModelProperty(value = "仓库编码")
	private String wareCode;

	@ApiModelProperty(value = "仓库名称")
	private String wareName;

	@ApiModelProperty(value = "联系人姓名")
	private String contactName;

	@ApiModelProperty(value = "联系人电话")
	private String contactPhone;

	@ApiModelProperty(value = "国家ID")
	private Long countryId;

	@ApiModelProperty(value = "省ID ")
	private Long provinceId;

	@ApiModelProperty(value = "市ID ")
	private Long cityId;

	@ApiModelProperty(value = "县ID")
	private Long countyId;

	@ApiModelProperty(value = "详细地址  ")
	private String address;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty("租户ID")
	@Encrypt
	private Long tenantId;

	@ApiModelProperty("删除标识")
	private Boolean deleteFlag;

	@ApiModelProperty("状态 true启用 false禁用")
	private Boolean status;


}
