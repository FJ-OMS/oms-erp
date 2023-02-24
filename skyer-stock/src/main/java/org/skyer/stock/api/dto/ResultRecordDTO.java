package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.stock.domain.entity.ResultDetail;

@Data
public class ResultRecordDTO {

    @ApiModelProperty("主键")
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
    @ApiModelProperty(value = "出库类型")
    private String outType;

    @ApiModelProperty(value = "包裹编码")
    private String packageCode;

    List<ResultDetail> detailList;
}
