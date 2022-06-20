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
@ApiModel("销售组织请求参数")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SalesOrganizationDTO {

	@ApiModelProperty("表ID，主键，供其他表做外键")
	@Id
	@GeneratedValue
	@Encrypt
	private Long id;

	@ApiModelProperty(value = "组织编码")
	private String orgCode;

	@ApiModelProperty(value = "组织名称")
	private String orgName;

	@ApiModelProperty(value = "租户ID")
	@Encrypt
	private Long tenantId;

	@ApiModelProperty(value = "营业执照名称")
	private String shopPermitName;

	@ApiModelProperty(value = "法人姓名")
	private String legalPerson;

	@ApiModelProperty(value = "统一社会信用代码")
	private String uniformSocialCreditCode;

	@ApiModelProperty(value = "状态 0启用 1禁用")
	private Boolean status;

	@ApiModelProperty("删除标识")
	private Boolean deleteFlag;

	@ApiModelProperty(value = "营业执照")
	private String businessLicense;

	@ApiModelProperty(value = "所属上级公司")
	@Encrypt
	private Long pid;

}
