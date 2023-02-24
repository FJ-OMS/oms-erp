package org.skyer.stock.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * 仓库表
 *
 * @author lixiaoyang 2021-07-28 15:31:35
 */
@ApiModel("仓库表")
@ModifyAudit
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sstock_warehouse")
public class Warehouse extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_TYPE_CODE = "typeCode";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_X = "x";
    public static final String FIELD_Y = "y";
    public static final String FIELD_STRUCTURE_ID = "structureId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("主键")
    @Id
    @GeneratedValue
    @Encrypt
    private Long id;
    @ApiModelProperty(value = "删除状态：true 删除")
    private Boolean deleteFlag;
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;
    @ApiModelProperty(value = "仓库名")
    private String name;
    @ApiModelProperty(value = "仓库编码")
    private String code;
    @ApiModelProperty(value = "仓库类型编码,STOCK.WAREHOUSE_TYPE")
    @LovValue(lovCode = "STOCK.WAREHOUSE_TYPE",meaningField = "typeCodeMeaning")
    private String typeCode;
    @ApiModelProperty(value = "状态：true启用,false禁用")
    private Boolean status;
    @ApiModelProperty(value = "结构ID")
    private Long structureId;

    private BigDecimal x;

    private BigDecimal y;

    @ApiModelProperty(value = "系统生成 1是 0否")
    private Boolean systemFlag;
    //
    // 非数据库字段
    // ------------------------------------------------------------------------------
    @ApiModelProperty(value = "仓库类型")
    @Transient
    private String typeCodeMeaning;

}
