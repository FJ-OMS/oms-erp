package org.skyer.order.infra.feign.entity.generate.odo.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

@ApiModel("出库单信息")
@Data
public class OutboundDeliveryOrderInfoDTO {

    @ApiModelProperty(value = "租户Id")
    @Encrypt
    private Long tenantId;

    @ApiModelProperty(value = "订单编号(内部)")
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

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;

    @ApiModelProperty(value = "店铺编码")
    private String shopCode;

    @ApiModelProperty(value = "渠道类型")
    private String channelTypeCode;

    @ApiModelProperty(value = "包裹编码")
    private String packageCode;

    @ApiModelProperty(value = "前置业务单id")
    @Encrypt
    private Long frontRecordId;

    @ApiModelProperty(value = "前置业务单号")
    private String frontRecordCode;

    @ApiModelProperty(value = "前置业务单类型")
    private String frontRecordType;

    @ApiModelProperty(value = "平台订单号（第三方）")
    private String platformOrderCode;

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

    @ApiModelProperty(value = "逻辑仓出库明细")
    private List<OutboundDeliveryOrderDetailLineDTO> detailList;
}
