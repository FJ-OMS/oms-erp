package org.skyer.stock.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 入库商品详情
 *
 * @author lixiaoyang 2021-10-25 16:34:17
 */
@ApiModel("入库商品详情")
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sstock_warehouse_warrant_detail")
public class WarehouseWarrantDetail {

    public static final String FIELD_ID = "id";
    public static final String FIELD_WAREHOUSE_WARRANT_ID = "warehouseWarrantId";
    public static final String FIELD_SKU_CODE = "skuCode";
    public static final String FIELD_SKU_NAME = "skuName";
    public static final String FIELD_SPU_CODE = "spuCode";
    public static final String FIELD_SPU_NAME = "spuName";
    public static final String FIELD_PLANNED_QTY = "plannedQty";
    public static final String FIELD_ALREADY_INPUT_QTY = "alreadyInputQty";
    public static final String FIELD_WAIT_INPUT_QTY = "waitInputQty";
    public static final String FIELD_CANCEL_QTY = "cancelQty";

    //
    // 数据库字段
    // ------------------------------------------------------------------------------

    @ApiModelProperty("主键")
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "出库单ID")
    private Long warehouseWarrantId;
    @ApiModelProperty(value = "商品SKU编码")
    private String skuCode;
    @ApiModelProperty(value = "sku名称")
    private String skuName;
    @ApiModelProperty(value = "商品编码")
    private String spuCode;
    @ApiModelProperty(value = "商品名称")
    private String spuName;
    @ApiModelProperty(value = "计划数量")
    private BigDecimal plannedQty;
    @ApiModelProperty(value = "已入库数量")
    private BigDecimal alreadyInputQty;
    @ApiModelProperty(value = "待入库数量")
    private BigDecimal waitInputQty;
    @ApiModelProperty(value = "取消数量")
    private BigDecimal cancelQty;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------


    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    public void initialize() {
        alreadyInputQty = BigDecimal.ZERO;
        waitInputQty = BigDecimal.ZERO;
        cancelQty = BigDecimal.ZERO;
    }


}
