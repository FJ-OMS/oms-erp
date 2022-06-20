package org.skyer.stock.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.mybatis.domain.AuditDomain;

/**
 * 仓库商品库存明细表
 *
 * @author lixiaoyang 2022-02-10 13:14:42
 */
@ApiModel("仓库商品库存明细表")
@ModifyAudit
@VersionAudit
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sstock_goods_stock_detail")
public class GoodsStockDetail extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_WAREHOUSE_CODE = "warehouseCode";
    public static final String FIELD_SKU_CODE = "skuCode";
    public static final String FIELD_STOCK_NUM = "stockNum";

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
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;
    @ApiModelProperty(value = "仓库code", required = true)
    @NotNull
    private String warehouseCode;
    @ApiModelProperty(value = "仓库id")
    private Long warehouseId;
    @ApiModelProperty(value = "sku编码", required = true)
    @NotBlank
    private String skuCode;
    @ApiModelProperty(value = "在库库存", required = true)
    @NotNull
    private BigDecimal stockNum;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------
    @ApiModelProperty(value = "变更数量", required = true)
    @Transient
    private BigDecimal changeNum;
    @ApiModelProperty(value = "变更前实际库存数量", required = true)
    @Transient
    private BigDecimal preRealNum;

}
