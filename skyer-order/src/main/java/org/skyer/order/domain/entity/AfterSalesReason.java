package org.skyer.order.domain.entity;

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
 * 售后原因
 *
 * @author zhanglongbing@ebc.com 2022-03-14 10:41:33
 */
@ApiModel("售后原因")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_after_sales_reason")
@Data
public class AfterSalesReason extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_REASON_CODE = "reasonCode";
    public static final String FIELD_REASON_DESCRIBE = "reasonDescribe";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_USE_NUM = "useNum";
    public static final String FIELD_USE_FLAG = "useFlag";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("")
    @Id
    @Encrypt
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "原因code",required = true)
    @NotBlank
    private String reasonCode;
   @ApiModelProperty(value = "原因描述")    
    private String reasonDescribe;
   @ApiModelProperty(value = "状态 0禁用1启用")    
    private Integer status;
   @ApiModelProperty(value = "被使用次数")    
    private Long useNum;
   @ApiModelProperty(value = "使用状态 0未使用 1已使用")    
    private Integer useFlag;
   @ApiModelProperty(value = "原因类型 0仅退款 1退货退款")    
    private Integer type;
   @ApiModelProperty(value = "删除状态 0正常 1被删除")
    private Integer deleteFlag;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------



}
