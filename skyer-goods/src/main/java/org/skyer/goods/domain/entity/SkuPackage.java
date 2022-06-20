package org.skyer.goods.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * 
 *
 * @author zhanglongbing@ebc.com 2022-03-03 10:45:30
 */
@ApiModel("")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_sku_package")
@Data
public class SkuPackage   {

    public static final String FIELD_ID = "id";
    public static final String FIELD_SKU_ID = "skuId";
    public static final String FIELD_PACKAGE_SKU_ID = "packageSkuId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("")
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "主表id",required = true)
    @NotNull
    private Long skuId;
    @ApiModelProperty(value = "套餐内skuId",required = true)
    @NotNull
    @Encrypt
    private Long packageSkuId;
    @ApiModelProperty(value = "套餐内sku数量",required = true)
    @NotNull
    private Long num;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------


}
