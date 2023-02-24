package org.skyer.stock.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lixiaoyang 2022-04-27 16:38:08
 */
@ApiModel("入库通知单物流信息")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
@Table(name = "sstock_warehouse_warrant_logistics")
public class WarehouseWarrantLogistics {

    public static final String FIELD_ID = "id";
    public static final String FIELD_LOGISTICS_COMPANY_ID = "logisticsCompanyId";
    public static final String FIELD_LOGISTICS_COMPANY_NAME = "logisticsCompanyName";
    public static final String FIELD_LOGISTICS_NO = "logisticsNo";
    public static final String FIELD_WAREHOUSE_WARRANT_ID = "warehouseWarrantId";

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
    @ApiModelProperty(value = "退货物流公司id")
    private Long logisticsCompanyId;
    @ApiModelProperty(value = "退货物流公司名称")
    private String logisticsCompanyName;
    @ApiModelProperty(value = "退货物流单号")
    private String logisticsNo;
    @ApiModelProperty(value = "入库通知单ID")
    private Long warehouseWarrantId;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------


}
