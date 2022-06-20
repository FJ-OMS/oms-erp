package org.skyer.stock.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * 入库商品详情
 *
 * @author xurongfa 2022-04-26 14:10:53
 */
@ApiModel("入库商品详情")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sstock_warehouse_result_detail")
@Data
public class WarehouseResultDetail {

    public static final String FIELD_ID = "id";
    public static final String FIELD_WAREHOUSE_RESULT_ID = "warehouseResultId";
    public static final String FIELD_SKU_CODE = "skuCode";
    public static final String FIELD_SKU_NAME = "skuName";
    public static final String FIELD_SPU_CODE = "spuCode";
    public static final String FIELD_SPU_NAME = "spuName";
    public static final String FIELD_ALREADY_QTY = "alreadyQty";

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
    @ApiModelProperty(value = "入库结果单ID")
    @Encrypt
    private Long warehouseResultId;
    @ApiModelProperty(value = "商品SKU编码")
    private String skuCode;
    @ApiModelProperty(value = "商品SKU名称")
    private String skuName;
    @ApiModelProperty(value = "商品编码")
    private String spuCode;
    @ApiModelProperty(value = "商品名称")
    private String spuName;
    @ApiModelProperty(value = "已入库数量")
    private BigDecimal alreadyQty;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

}
