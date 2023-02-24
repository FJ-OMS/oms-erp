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
 * sku属性关联表
 *
 * @author zhanglongbing@ebc.com 2021-12-28 09:17:10
 */
@ApiModel("sku属性关联表")
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_sku_attribute_value")
public class SkuAttributeValue  {

    public static final String FIELD_ID = "id";
    public static final String FIELD_SKU_ID = "skuId";
    public static final String FIELD_ATTRIBUTE_ID = "attributeId";
    public static final String FIELD_ATTRIBUTE_NAME = "attributeName";
    public static final String FIELD_ATTRIBUTE_VALUE = "attributeValue";
    public static final String FIELD_ATTRIBUTE_TYPE = "attributeType";
    public static final String FIELD_IS_SKU = "isSku";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("属性值")
    @Id
	@Encrypt
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "",required = true)
	@Encrypt
    @NotNull
    private Long skuId;
	@Encrypt
    @ApiModelProperty(value = "属性ID")
    private Long attributeId;
    @ApiModelProperty(value = "属性名称",required = true)
    @NotBlank
    private String attributeName;
    @ApiModelProperty(value = "属性值",required = true)
    @NotBlank
    private String attributeValue;
    @ApiModelProperty(value = "类型： 系统属性0  分类属性  1  商品属性2  附加属性3")
    private Integer attributeType;
    @ApiModelProperty(value = "是否用于生成sku",required = true)
    @NotNull
    private Boolean isSku;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------


}
