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

/**
 * 入库单
 *
 * @author lixiaoyang 2021-10-25 16:34:17
 */
@ApiModel("入库单")
@VersionAudit
@ModifyAudit
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sstock_warehouse_warrant")
public class WarehouseWarrant extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_INPUT_TYPE = "inputType";
    public static final String FIELD_RETURNS_NAME = "returnsName";
    public static final String FIELD_RETURNS_ADDRESS = "returnsAddress";
    public static final String FIELD_RETURNS_TEL = "returnsTel";
    public static final String FIELD_RETURNS_MOBILE = "returnsMobile";
    public static final String FIELD_COUNTRY = "country";
    public static final String FIELD_PROVINCE = "province";
    public static final String FIELD_CITY = "city";
    public static final String FIELD_DISTRICT = "district";
    public static final String FIELD_SOR_BUYER_NIKE_NAME = "sorBuyerNikeName";
    public static final String FIELD_ORDER_MSG = "orderMsg";
    public static final String FIELD_BUY_MSG = "buyMsg";
    public static final String FIELD_SUPPORT_LOGIC_WAREHOUSE_ID = "supportLogicWarehouseId";
    public static final String FIELD_SUPPORT_LOGIC_WAREHOUSE_NAME = "supportLogicWarehouseName";
    public static final String FIELD_SUPPORT_LOGIC_WAREHOUSE_CODE = "supportLogicWarehouseCode";
    public static final String FIELD_RECEIVING_LOGIC_WAREHOUSE_ID = "receivingLogicWarehouseId";
    public static final String FIELD_RECEIVING_LOGIC_WAREHOUSE_NAME = "receivingLogicWarehouseName";
    public static final String FIELD_RECEIVING_LOGIC_WAREHOUSE_CODE = "receivingLogicWarehouseCode";
    public static final String FIELD_STORE_ID = "storeId";
    public static final String FIELD_STORE_NAME = "storeName";
    public static final String FIELD_CHANNEL_TYPE_CODE = "channelTypeCode";
    public static final String FIELD_REAL_NOTICE_CODE = "realNoticeCode";
    public static final String FIELD_FRONT_RECORD_TYPE = "frontRecordType";
    public static final String FIELD_FRONT_RECORD_ID = "frontRecordId";
    public static final String FIELD_FRONT_RECORD_CODE = "frontRecordCode";
    public static final String FIELD_PLATFORM_ORDER_CODE = "platformOrderCode";
    public static final String FIELD_ORDER_CODE = "orderCode";
    public static final String FIELD_RESULT_CODE = "resultCode";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_FAILURE_STATUS = "failureStatus";
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
    private Long id;
    @ApiModelProperty(value = "编号")
    private String code;
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;
    @ApiModelProperty(value = "入库类型 STOCK.INPUT_TYPE")
    private String inputType;
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
    @ApiModelProperty(value = "买家昵称")
    private String sorBuyerNikeName;
    @ApiModelProperty(value = "订单备注")
    private String orderMsg;
    @ApiModelProperty(value = "客户留言")
    private String buyMsg;
    @ApiModelProperty(value = "供货逻辑仓ID")
    private Long supportLogicWarehouseId;
    @ApiModelProperty(value = "供货逻辑仓名称")
    private String supportLogicWarehouseName;
    @ApiModelProperty(value = "供货逻辑仓编码")
    private String supportLogicWarehouseCode;
    @ApiModelProperty(value = "收货逻辑仓ID")
    private Long receivingLogicWarehouseId;
    @ApiModelProperty(value = "收货逻辑仓名称")
    private String receivingLogicWarehouseName;
    @ApiModelProperty(value = "收货逻辑仓编码")
    private String receivingLogicWarehouseCode;
    @ApiModelProperty(value = "店铺id")
    private Long storeId;
    @ApiModelProperty(value = "销售店名")
    private String storeName;
    @ApiModelProperty(value = "渠道类型 SCHL.CHANNEL_TYPE")
    private String channelTypeCode;
    @ApiModelProperty(value = "收发货通知单编号")
    private String realNoticeCode;
    @ApiModelProperty(value = "前置业务单类型 HPFM.FRONT_RECORD_TYPE")
    private String frontRecordType;
    @ApiModelProperty(value = "前置业务单id")
    private Long frontRecordId;
    @ApiModelProperty(value = "前置业务单号")
    private String frontRecordCode;
    @ApiModelProperty(value = "三方订单号")
    private String platformOrderCode;
    @ApiModelProperty(value = "内部单号")
    private String orderCode;
    @ApiModelProperty(value = "收发货结果单id(多条结果单按照,隔开)")
    private String resultCode;
    @ApiModelProperty(value = "状态 STOCK.INPUT_STATUS")
    private String status;
    @ApiModelProperty(value = "出库失败附加状态 0退换货时 1中止发货时")
    private Integer failureStatus;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //  销售退货入库
    @Transient
    public final static String  INPUT_TYPE_SALES_RETURN_STORAGE = "sales-return-storage";


    // 待入库
    @Transient
    public final static String STATUS_INPUT_STOCK_PENDING = "input-stock-pending";


}
