package org.skyer.stock.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * 收发货结果单
 *
 * @author wushaochuan 2021-08-26 17:17:03
 */
@ApiModel("收发货结果单")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sstock_result_record")
@Data
public class ResultRecord extends AuditDomain {
    public static final String FIELD_ID = "id";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_ORDER_ID = "orderId";
    public static final String FIELD_ORDER_CODE = "orderCode";
    public static final String FIELD_RESULT_CODE = "resultCode";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_RECEIVE_WAREHOUSE_ID = "receiveWarehouseId";
    public static final String FIELD_RECEIVE_WAREHOUSE_CODE = "receiveWarehouseCode";
    public static final String FIELD_RECEIVE_WAREHOUSE_NAME = "receiveWarehouseName";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_ODO_ID = "odoId";
    public static final String FIELD_ODO_CODE = "odoCode";
    public static final String FIELD_WMS_CODE = "wmsCode";
    public static final String FIELD_SHIPPING_NO = "shippingNo";
    public static final String FIELD_DELIVERY_TYPE = "deliveryType";
    public static final String FIELD_RECEIVER_NAME = "receiverName";
    public static final String FIELD_RECEIVER_ADDRESS = "receiverAddress";
    public static final String FIELD_RECEIVER_TEL = "receiverTel";
    public static final String FIELD_RECEIVER_MOBILE = "receiverMobile";
    public static final String FIELD_RECEIVER_COUNTRY = "receiverCountry";
    public static final String FIELD_RECEIVER_PROVINCE = "receiverProvince";
    public static final String FIELD_RECEIVER_CITY = "receiverCity";
    public static final String FIELD_RECEIVER_DISTRICT = "receiverDistrict";
    public static final String FIELD_OUT_LOGIC_WAREHOUSE_ID = "outLogicWarehouseId";
    public static final String FIELD_OUT_LOGIC_WAREHOUSE_NAME = "outLogicWarehouseName";
    public static final String FIELD_OUT_LOGIC_WAREHOUSE_CODE = "outLogicWarehouseCode";
    public static final String FIELD_LOGISTIC_COMPANY = "logisticCompany";
    public static final String FIELD_LOGISTIC_COMPANY_CODE = "logisticCompanyCode";
    public static final String FIELD_LOGISTIC_COMPANY_ID = "logisticCompanyId";
    public static final String FIELD_LOGISTIC_BUSINESS = "logisticBusiness";
    public static final String FIELD_LOGISTIC_BUSINESS_CODE = "logisticBusinessCode";
    public static final String FIELD_LOGISTIC_BUSINESS_ID = "logisticBusinessId";
    public static final String FIELD_SHOP_ID = "shopId";
    public static final String FIELD_SHOP_CODE = "shopCode";
    public static final String FIELD_SHOP_NAME = "shopName";
    public static final String FIELD_CHANNEL_TYPE_CODE = "channelTypeCode";
    public static final String FIELD_OUT_TYPE = "outType";
    public static final String FIELD_POSTAGE = "postage";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    // 状态常量 全部出库
    public static final Long STATUS_ALL_OUT = 0L;

    //
    // 数据库字段
    // ------------------------------------------------------------------------------

    @ApiModelProperty("主键")
    @Id
    @GeneratedValue
    @Encrypt
    private Long id;
    @ApiModelProperty(value = "订单id")
    @Encrypt
    private Long orderId;
    @ApiModelProperty(value = "订单编号")
    private String orderCode;
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;
    @ApiModelProperty(value = "结果单编码")
    private String resultCode;
    @ApiModelProperty(value = "状态 0全部出库")
    private Long status;
    @ApiModelProperty(value = "收货逻辑仓id")
    private Long receiveWarehouseId;
    @ApiModelProperty(value = "收货逻辑仓编码")
    private String receiveWarehouseCode;
    @ApiModelProperty(value = "收货逻辑仓名称")
    private String receiveWarehouseName;

    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "出库通知单id(sstock_outbound_delivery_order.id)")
    @Encrypt
    private Long odoId;
    @ApiModelProperty(value = "出库通知单号")
    private String odoCode;
    @ApiModelProperty(value = "wms单号")
    private String wmsCode;
    @ApiModelProperty(value = "运单号")
    private String shippingNo;
    @ApiModelProperty(value = "收发类型 0:发1:收")
    private Integer deliveryType;
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
    @ApiModelProperty(value = "店铺ID")
    @Encrypt
    private Long shopId;
    @ApiModelProperty(value = "店铺编码")
    private String shopCode;
    @ApiModelProperty(value = "店铺名称")
    private String shopName;
    @ApiModelProperty(value = "渠道类型")
    private String channelTypeCode;
    @ApiModelProperty(value = "出库类型 ")
    private String outType;
    @ApiModelProperty(value = "包裹编码")
    private String packageCode;
    @ApiModelProperty(value = "运费")
    private BigDecimal postage;
}
