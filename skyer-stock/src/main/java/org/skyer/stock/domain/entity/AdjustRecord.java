package org.skyer.stock.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.stock.infra.constant.ObjectConstant;

/**
 * 库存调整单
 *
 * @author lixiaoyang 2021-08-02 16:44:33
 */
@ApiModel("库存调整单")
@VersionAudit
@ModifyAudit
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sstock_adjust_record")
public class AdjustRecord extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_ADJUST_CODE = "adjustCode";
    public static final String FIELD_WAREHOUSE_ID = "warehouseId";
    public static final String FIELD_AUDIT_STATUS = "auditStatus";
    public static final String FIELD_LOGIC_STATUS = "logicStatus";
    public static final String FIELD_REAL_STATUS = "realStatus";
    public static final String FIELD_REMARKS = "remarks";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------


    public AdjustRecord() {
        this.deleteFlag = ObjectConstant.DeleteFlag.NO;
    }
    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("主键")
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "删除状态")
    private Boolean deleteFlag;
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;
    @ApiModelProperty(value = "调整单号")
    private String adjustCode;
    @ApiModelProperty(value = "仓库编码")
    private Long warehouseId;
    @ApiModelProperty(value = "状态STOCK.RECORD_STATUS")
    private String auditStatus;
    @ApiModelProperty(value = "逻辑仓增加状态 1已执行 0未执行")
    private Boolean logicStatus;
    @ApiModelProperty(value = "实仓增加状态 1已执行 0未执行")
    private Boolean realStatus;
    @ApiModelProperty(value = "备注 ")
    private String remarks;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------
    @Transient
    private  Long structureId;
}
