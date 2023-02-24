package org.skyer.goods.domain.entity;


import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.experimental.Accessors;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.MultiLanguage;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.Date;

/**
 * <p>
 * 商品品牌表
 * </p>
 *
 * @author wusc
 * @since 2021-06-24
 */
@ModifyAudit
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name="sgoo_brand")
@ApiModel(description="商品品牌表")
public class Brand extends AuditDomain {

    public static String NAME = "name";

    public static final String DELETE_FLAG = "deleteFlag";
    public static final String CODE = "code";
    public static final String TENANT_ID = "tenantId";
    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @GeneratedValue
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "logo")
    private String logo;

    @ApiModelProperty(value = "生成商")
    private String manufacturer;

    @ApiModelProperty(value = "品牌名称")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态1,启用；0禁用")
    private Integer status;

    @ApiModelProperty(value = "删除状态")
    private Boolean deleteFlag;

    @ApiModelProperty(value = "租户Id")
    @Encrypt
    private Long tenantId;

    public Brand(){
        this.deleteFlag = false;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag == null ? false : deleteFlag;
    }
}
