package org.skyer.channel.domain.entity;


import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import java.util.List;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.MultiLanguage;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * <p>
 * 
 * </p>
 *
 * @author lzh
 * @since 2021-06-18
 */
@VersionAudit
@ModifyAudit
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name="channel_sales_organization")
public class SalesOrganization extends AuditDomain {

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "公司编码")
    private String orgCode;

    @ApiModelProperty(value = "公司名称")
    @NotNull
    private String orgName;

    @ApiModelProperty(value = "租户ID")
    @Encrypt
    private Long tenantId;

    @ApiModelProperty(value = "营业执照名称")
    @NotNull
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

    @ApiModelProperty(value = "所属集团")
    @Encrypt
    private Long groupId;

    @Transient
    private List<SalesOrganization> children;
}
