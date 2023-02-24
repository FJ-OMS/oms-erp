package org.skyer.stock.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * 出库单
 *
 * @author lixiaoyang 2021-10-15 15:24:26
 */
@ApiModel("出库单")
@VersionAudit
@ModifyAudit
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sstock_outbound_delivery_order")
public class OutboundDeliveryOrder extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_ORDER_CODE = "orderCode";
    public static final String FIELD_ORDER_ID = "orderId";
    public static final String FIELD_OUT_TYPE = "outType";
    public static final String FIELD_RECEIVER_NAME = "receiverName";
    public static final String FIELD_RECEIVER_ADDRESS = "receiverAddress";
    public static final String FIELD_RECEIVER_TEL = "receiverTel";
    public static final String FIELD_RECEIVER_MOBILE = "receiverMobile";
    public static final String FIELD_RECEIVER_COUNTRY = "receiverCountry";
    public static final String FIELD_RECEIVER_PROVINCE = "receiverProvince";
    public static final String FIELD_RECEIVER_CITY = "receiverCity";
    public static final String FIELD_RECEIVER_DISTRICT = "receiverDistrict";
    public static final String FIELD_SOR_BUYER_NIKE_NAME = "sorBuyerNikeName";
    public static final String FIELD_SCHEDULED_DELIVERY_TIME = "scheduledDeliveryTime";
    public static final String FIELD_ORDER_MSG = "orderMsg";
    public static final String FIELD_BUY_MSG = "buyMsg";
    public static final String FIELD_RECEIVING_LOGIC_WAREHOUSE_ID = "receivingLogicWarehouseId";
    public static final String FIELD_RECEIVING_LOGIC_WAREHOUSE_NAME = "receivingLogicWarehouseName";
    public static final String FIELD_RECEIVING_LOGIC_WAREHOUSE_CODE = "receivingLogicWarehouseCode";
    public static final String FIELD_SHOP_ID = "shopId";
    public static final String FIELD_SHOP_CODE = "shopCode";
    public static final String FIELD_SHOP_NAME = "shopName";
    public static final String FIELD_CHANNEL_TYPE_CODE = "channelTypeCode";
    public static final String FIELD_FRONT_RECORD_ID = "frontRecordId";
    public static final String FIELD_FRONT_RECORD_CODE = "frontRecordCode";
    public static final String FIELD_FRONT_RECORD_TYPE = "frontRecordType";
    public static final String FIELD_PACKAGE_CODE = "packageCode";
    public static final String FIELD_PLATFORM_ORDER_CODE = "platformOrderCode";
    public static final String FIELD_RESULT_CODE = "resultCode";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_OUT_LOGIC_WAREHOUSE_ID = "outLogicWarehouseId";
    public static final String FIELD_OUT_LOGIC_WAREHOUSE_NAME = "outLogicWarehouseName";
    public static final String FIELD_OUT_LOGIC_WAREHOUSE_CODE = "outLogicWarehouseCode";
    public static final String FIELD_LOGISTIC_COMPANY = "logisticCompany";
    public static final String FIELD_LOGISTIC_COMPANY_CODE = "logisticCompanyCode";
    public static final String FIELD_LOGISTIC_COMPANY_ID = "logisticCompanyId";
    public static final String FIELD_LOGISTIC_BUSINESS = "logisticBusiness";
    public static final String FIELD_LOGISTIC_BUSINESS_CODE = "logisticBusinessCode";
    public static final String FIELD_LOGISTIC_BUSINESS_ID = "logisticBusinessId";
    //
    // 状态常量
    // ------------------------------------------------------------------------------

    @Transient
    public final Integer STATUS_TO_BE_AGREED_WMS = 0;
    @Transient
    public final Integer STATUS_AWAIT_STOCK_OUT = 1;
    //全部出库
    @Transient
    public final Integer STATUS_ALL_STOCK_OUT = 3;
    // 部分出库
    @Transient
    public final Integer STATUS_PART_STOCK_OUT = 4;
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
    @ApiModelProperty(value = "编号")
    private String code;
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;
    @ApiModelProperty(value = "订单编号")
    private String orderCode;
    @ApiModelProperty(value = "手工单订单ID")
    @Encrypt
    private Long orderId;
    @ApiModelProperty(value = "出库类型")
    private String outType;
    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;
    @ApiModelProperty(value = "收货人地址")
    private String receiverAddress;
    @ApiModelProperty(value = "收货人电话")
    private String receiverTel;
    @ApiModelProperty(value = "收货人手机")
    private String receiverMobile;
    @ApiModelProperty(value = "国家")
    private String receiverCountry;
    @ApiModelProperty(value = "省")
    private String receiverProvince;
    @ApiModelProperty(value = "市")
    private String receiverCity;
    @ApiModelProperty(value = "区")
    private String receiverDistrict;
    @ApiModelProperty(value = "买家昵称")
    private String sorBuyerNikeName;
    @ApiModelProperty(value = "计划发货时间")
    private Date scheduledDeliveryTime;
    @ApiModelProperty(value = "订单备注")
    private String orderMsg;
    @ApiModelProperty(value = "客户留言")
    private String buyMsg;
    @ApiModelProperty(value = "收货逻辑仓ID")
    private Long receivingLogicWarehouseId;
    @ApiModelProperty(value = "收货逻辑仓名称")
    private String receivingLogicWarehouseName;
    @ApiModelProperty(value = "收货逻辑仓编码")
    private String receivingLogicWarehouseCode;

    @ApiModelProperty(value = "店铺ID")
    @Encrypt
    private Long shopId;
    @ApiModelProperty(value = "店铺编码")
    private String shopCode;
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "渠道类型")
    private String channelTypeCode;

    @ApiModelProperty(value = "前置业务单id")
    @Encrypt
    private Long frontRecordId;
    @ApiModelProperty(value = "前置业务单号")
    private String frontRecordCode;
    @ApiModelProperty(value = "前置业务单类型")
    private String frontRecordType;

    @ApiModelProperty(value = "包裹编码")
    private String packageCode;
    @ApiModelProperty(value = "平台订单号")
    private String platformOrderCode;
    @ApiModelProperty(value = "收发货结果单id(多条结果单按照,隔开)")
    private String resultCode;
    @ApiModelProperty(value = "状态 0待通知WMS 1待出库 2通知WMS失败 3全部出库 4部分出库 5取消出库")
    private Integer status;

    @ApiModelProperty(value = "发货逻辑仓ID")
    @Encrypt
    private Long outLogicWarehouseId;
    @ApiModelProperty(value = "发货逻辑仓名称")
    private String outLogicWarehouseName;
    @ApiModelProperty(value = "发货逻辑仓编码")
    private String outLogicWarehouseCode;

    @ApiModelProperty(value = "物流公司")
    private String logisticCompany;
    @ApiModelProperty(value = "物流编号")
    private String logisticCompanyCode;
    @ApiModelProperty(value = "物流ID")
    @Encrypt
    private Long logisticCompanyId;

    @ApiModelProperty(value = "物流业务")
    private String logisticBusiness;
    @ApiModelProperty(value = "物流业务编码")
    private String logisticBusinessCode;
    @ApiModelProperty(value = "物流业务ID")
    @Encrypt
    private Long logisticBusinessId;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------
}
