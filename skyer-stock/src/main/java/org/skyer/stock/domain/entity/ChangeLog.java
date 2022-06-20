package org.skyer.stock.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.stock.infra.constant.ObjectConstant;

/**
 * 库存流水表
 *
 * @author lixiaoyang 2021-08-03 16:51:06
 */
@ApiModel("库存流水表")
@VersionAudit
@ModifyAudit
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sstock_change_log")
public class ChangeLog extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_TURNOVER_CODE = "turnoverCode";
    public static final String FIELD_RECORD_CODE = "recordCode";
    public static final String FIELD_IN_OUT_TYPE = "inOutType";
    public static final String FIELD_RECORD_TYPE_CODE = "recordTypeCode";
    public static final String FIELD_SKU_CODE = "skuCode";
    public static final String FIELD_PRE_REAL_NUM = "preRealNum";
    public static final String FIELD_CHANGE_NUM = "changeNum";
    public static final String FIELD_AFT_REAL_NUM = "aftRealNum";
    public static final String FIELD_QTY_TYPE = "qtyType";
    public static final String FIELD_RECORD_ID = "recordId";
    public static final String FIELD_WAREHOUSE_ID = "warehouseId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    public ChangeLog() {
        this.deleteFlag = ObjectConstant.DeleteFlag.NO;
    }

    @ApiModelProperty("主键")
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "删除状态")
    private Boolean deleteFlag;
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;
    @ApiModelProperty(value = "流水编号", required = true)
    @NotBlank
    private String turnoverCode;
    @ApiModelProperty(value = "单据编码", required = true)
    @NotBlank
    private String recordCode;
    @ApiModelProperty(value = "出入库类型(1入库，2出库)")
    private Integer inOutType;
    @ApiModelProperty(value = "业务单据类型--值集")
    private String recordTypeCode;
    @ApiModelProperty(value = "商品sku", required = true)
    @NotBlank
    private String skuCode;
    @ApiModelProperty(value = "变更前实际库存数量", required = true)
    @NotNull
    private BigDecimal preRealNum;
    @ApiModelProperty(value = "变更数量", required = true)
    @NotNull
    private BigDecimal changeNum;
    @ApiModelProperty(value = "变更后实际库存数量", required = true)
    @NotNull
    private BigDecimal aftRealNum;
    @ApiModelProperty(value = "仓库库存类型,STOCK.QTY_TYPE")
    @LovValue(lovCode = "STOCK.QTY_TYPE",meaningField = "typeCodeMeaning")
    private String qtyTypeCode;
    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;

    @ApiModelProperty("备注")
    private String remark;
    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

}
