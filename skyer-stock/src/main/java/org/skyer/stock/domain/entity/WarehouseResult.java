package org.skyer.stock.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * 入库结果单
 *
 * @author xurongfa 2022-04-26 14:10:52
 */
@ApiModel("入库结果单")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sstock_warehouse_result")
@Data
public class WarehouseResult extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_WAREHOUSE_WARRANT_ID = "warehouseWarrantId";
    public static final String FIELD_WAREHOUSE_WARRANT_CODE = "warehouseWarrantCode";
    public static final String FIELD_PLATFORM_ORDER_CODE = "platformOrderCode";
    public static final String FIELD_ORDER_CODE = "orderCode";
    public static final String FIELD_INPUT_TYPE = "inputType";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_SUPPORT_LOGIC_WAREHOUSE_ID = "supportLogicWarehouseId";
    public static final String FIELD_SUPPORT_LOGIC_WAREHOUSE_NAME = "supportLogicWarehouseName";
    public static final String FIELD_SUPPORT_LOGIC_WAREHOUSE_CODE = "supportLogicWarehouseCode";
    public static final String FIELD_RECEIVING_LOGIC_WAREHOUSE_ID = "receivingLogicWarehouseId";
    public static final String FIELD_RECEIVING_LOGIC_WAREHOUSE_NAME = "receivingLogicWarehouseName";
    public static final String FIELD_RECEIVING_LOGIC_WAREHOUSE_CODE = "receivingLogicWarehouseCode";
    public static final String FIELD_STORE_ID = "storeId";
    public static final String FIELD_STORE_NAME = "storeName";
    public static final String FIELD_CHANNEL_TYPE_CODE = "channelTypeCode";
    public static final String FIELD_RETURNS_NAME = "returnsName";
    public static final String FIELD_RETURNS_ADDRESS = "returnsAddress";
    public static final String FIELD_RETURNS_TEL = "returnsTel";
    public static final String FIELD_RETURNS_MOBILE = "returnsMobile";
    public static final String FIELD_COUNTRY = "country";
    public static final String FIELD_PROVINCE = "province";
    public static final String FIELD_CITY = "city";
    public static final String FIELD_DISTRICT = "district";
    public static final String FIELD_LOGISTICS_COMPANY_ID = "logisticsCompanyId";
    public static final String FIELD_LOGISTICS_COMPANY_NAME = "logisticsCompanyName";
    public static final String FIELD_LOGISTICS_NO = "logisticsNo";

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
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;
    @ApiModelProperty(value = "入库结果单号")
    private String code;
    @ApiModelProperty(value = "入库通知单id")
    private Long warehouseWarrantId;
    @ApiModelProperty(value = "入库通知单号")
    private String warehouseWarrantCode;
    @ApiModelProperty(value = "三方订单号")
    private String platformOrderCode;
    @ApiModelProperty(value = "内部单号")
    private String orderCode;
    @ApiModelProperty(value = "入库类型 STOCK.INPUT_TYPE")
    private String inputType;
    @ApiModelProperty(value = "入库状态 STOCK.INPUT_RESULT_STATUS")
    private String status;
    @ApiModelProperty(value = "供货逻辑仓ID")
    @Encrypt
    private Long supportLogicWarehouseId;
    @ApiModelProperty(value = "供货逻辑仓名称")
    private String supportLogicWarehouseName;
    @ApiModelProperty(value = "供货逻辑仓编码")
    private String supportLogicWarehouseCode;
    @ApiModelProperty(value = "收货逻辑仓ID")
    @Encrypt
    private Long receivingLogicWarehouseId;
    @ApiModelProperty(value = "收货逻辑仓名称")
    private String receivingLogicWarehouseName;
    @ApiModelProperty(value = "收货逻辑仓编码")
    private String receivingLogicWarehouseCode;
    @ApiModelProperty(value = "店铺id")
    @Encrypt
    private Long storeId;
    @ApiModelProperty(value = "店铺名")
    private String storeName;
    @ApiModelProperty(value = "渠道类型 SCHL.CHANNEL_TYPE")
    private String channelTypeCode;
    @ApiModelProperty(value = "退货人姓名")
    private String returnsName;
    @ApiModelProperty(value = "退货人地址")
    private String returnsAddress;
    @ApiModelProperty(value = "退货人电话")
    private String returnsTel;
    @ApiModelProperty(value = "退货人手机")
    private String returnsMobile;
    @ApiModelProperty(value = "国家")
    private String country;
    @ApiModelProperty(value = "省")
    private String province;
    @ApiModelProperty(value = "市")
    private String city;
    @ApiModelProperty(value = "区")
    private String district;
    @ApiModelProperty(value = "退货物流公司id")
    @Encrypt
    private Long logisticsCompanyId;
    @ApiModelProperty(value = "退货物流公司名称")
    private String logisticsCompanyName;
    @ApiModelProperty(value = "退货物流单号")
    private String logisticsNo;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------
    @Transient
    public final static String STATUS_ALL_SUCCESS = "all-success";

}
