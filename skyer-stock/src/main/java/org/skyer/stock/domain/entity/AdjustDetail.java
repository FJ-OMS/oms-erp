package org.skyer.stock.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.stock.infra.constant.ObjectConstant;

/**
 * 库存调整单详情
 *
 * @author lixiaoyang 2021-08-02 16:44:33
 */
@ApiModel("库存调整单详情")
@VersionAudit
@ModifyAudit
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sstock_adjust_detail")
public class AdjustDetail extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_ADJUST_ID = "adjustId";
    public static final String FIELD_SKU_CODE = "skuCode";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_PLAN_NUM = "planNum";
    public static final String FIELD_REALITY_NUM = "realityNum";
    public static final String FIELD_CANCEL_NUM = "cancelNum";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    public AdjustDetail() {
        this.deleteFlag = ObjectConstant.DeleteFlag.NO;
    }

    @ApiModelProperty("主键")
    @Id
    @GeneratedValue
    @Encrypt
    private Long id;
    @ApiModelProperty(value = "删除状态")
    private Boolean deleteFlag;
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;
    @ApiModelProperty(value = "调整单id")
    private Long adjustId;
    @ApiModelProperty(value = "商品code")
    private String skuCode;
    @ApiModelProperty("仓库id")
    private Long warehouseId;
    @ApiModelProperty(value = "类型 1 增加 2减少")
    private Integer type;
    @ApiModelProperty(value = "计划调整数量")
    private BigDecimal planNum;
    @ApiModelProperty(value = "实际调整数量")
    private BigDecimal realityNum;
    @ApiModelProperty(value = "取消数量 ")
    private BigDecimal cancelNum;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

}
