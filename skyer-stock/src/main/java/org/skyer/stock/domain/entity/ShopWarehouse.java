package org.skyer.stock.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 店铺和仓库关联表
 *
 * @author lixiaoyang 2021-08-02 11:03:47
 */
@ApiModel("店铺和仓库关联表")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sstock_shop_warehouse")
public class ShopWarehouse {

    public static final String FIELD_ID = "id";
    public static final String FIELD_SHOP_ID = "shopId";
    public static final String FIELD_SHOP_CODE = "shopCode";
    public static final String FIELD_WAREHOUSE_ID = "warehouseId";
    public static final String FIELD_WAREHOUSE_CODE = "warehouseCode";
    
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
    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "店铺编号")
    private String shopCode;
    @ApiModelProperty(value = "渠道类型")
    private String channelTypeCode;
    @ApiModelProperty(value = "仓库ID")
    private Long warehouseId;
    @ApiModelProperty(value = "仓库编号")
    private String warehouseCode;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

}
