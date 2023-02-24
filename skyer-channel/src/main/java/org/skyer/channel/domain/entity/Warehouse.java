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
import javax.validation.constraints.NotNull;

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
@Table(name="channel_warehouse")
public class Warehouse extends AuditDomain {

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "仓库编码")
    private String wareCode;

    @ApiModelProperty(value = "仓库名称")
    @NotNull
    private String wareName;

    @ApiModelProperty(value = "联系人姓名")
    @NotNull
    private String contactName;

    @ApiModelProperty(value = "联系人电话")
    @NotNull
    private String contactPhone;

    @ApiModelProperty(value = "国家ID")
    @NotNull
    private Long countryId;

    @ApiModelProperty(value = "省ID ")
    @NotNull
    private Long provinceId;

    @ApiModelProperty(value = "市ID ")
    @NotNull
    private Long cityId;

    @ApiModelProperty(value = "县ID")
    @NotNull
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

    @ApiModelProperty(value = "逻辑仓名称")
    private String logicWarehouseName;

    @ApiModelProperty(value = "是否逻辑仓    1-是  0-否")
    private Boolean logicFlag;

}
