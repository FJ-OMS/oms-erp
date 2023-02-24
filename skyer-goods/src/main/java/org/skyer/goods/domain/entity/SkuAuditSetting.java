package org.skyer.goods.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品SKU审核
 *
 * @author zhanglongbing@ebc.com 2021-12-27 10:09:56
 */
@ApiModel("商品SKU审核")
@VersionAudit
@ModifyAudit
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_sku_audit_setting")
public class SkuAuditSetting extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_CREATED_AUDIT_STATUS = "createdAuditStatus";
    public static final String FIELD_UPDATE_AUDIT_STATUS = "updateAuditStatus";
    public static final String FIELD_UPDATE_PRICE_STATUS = "updatePriceStatus";
    public static final String FIELD_UPDATE_PROPERTY_STATUS = "updatePropertyStatus";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("主键")
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "创建审核状态 1开启 0关闭",required = true)
    @NotNull
    private Integer createdAuditStatus;
    @ApiModelProperty(value = "更改审核状态 1开启 0关闭",required = true)
    @NotNull
    private Integer updateAuditStatus;
   @ApiModelProperty(value = "更改价格状态 1开启 0关闭")    
    private Integer updatePriceStatus;
   @ApiModelProperty(value = "更改属性状态 1开启 0关闭")    
    private Integer updatePropertyStatus;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    /**
     * 关闭状态
     */
	@Transient
	public static final Integer STATUS_OFF = 0;
    /**
     * 开启状态
     */
    @Transient
    public static final Integer STATUS_ON = 1;


}
