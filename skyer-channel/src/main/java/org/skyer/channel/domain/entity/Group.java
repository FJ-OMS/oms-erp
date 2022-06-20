package org.skyer.channel.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import org.skyer.mybatis.domain.AuditDomain;

import java.math.BigDecimal;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.starter.keyencrypt.core.Encrypt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author linzhuanghuang@ebc.com 2021-09-03 16:54:36
 */
@ApiModel("")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "channel_group")
@Data
public class Group extends AuditDomain {

	public static final String FIELD_ID = "id";
	public static final String FIELD_GROUP_NAME = "groupName";
	public static final String FIELD_GROUP_SHORT_NAME = "groupShortName";
	public static final String FIELD_LOGO = "logo";
	public static final String FIELD_FAVICON = "favicon";
	public static final String FIELD_TENANT_ID = "tenantId";

	//
	// 业务方法(按public protected private顺序排列)
	// ------------------------------------------------------------------------------

	//
	// 数据库字段
	// ------------------------------------------------------------------------------


	@ApiModelProperty("")
	@Id
	@GeneratedValue
	@Encrypt
	private Long id;
	@ApiModelProperty(value = "集团名称")
	private String groupName;
	@ApiModelProperty(value = "集团简称")
	private String groupShortName;
	@ApiModelProperty(value = "集团logo")
	private String logo;
	@ApiModelProperty(value = "集团图标")
	private String favicon;



	//
	// 非数据库字段
	// ------------------------------------------------------------------------------

	//
	// getter/setter
	// ------------------------------------------------------------------------------


}
