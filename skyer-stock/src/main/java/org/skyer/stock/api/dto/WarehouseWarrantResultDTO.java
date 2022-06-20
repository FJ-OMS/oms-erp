package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WarehouseWarrantResultDTO {

    @ApiModelProperty("通知单ID")
    private Long wwId;
    @ApiModelProperty(value = "编号")
    private String wwCode;
    @ApiModelProperty(value = "租户Id")
    private Long tenantId;
    @ApiModelProperty(value = "入库类型")
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
    @ApiModelProperty(value = "渠道类型")
    private String channelTypeCode;
    @ApiModelProperty(value = "收发货通知单编号")
    private String realNoticeCode;
    @ApiModelProperty(value = "前置业务单号类型")
    private String frontRecordType;
    @ApiModelProperty(value = "前置业务单id")
    private Long frontRecordId;
    @ApiModelProperty(value = "前置业务单号")
    private String frontRecordCode;
    @ApiModelProperty(value = "三方订单号")
    private String platformOrderCode;
    @ApiModelProperty(value = "内部单号")
    private String orderCode;
    @ApiModelProperty(value = "出库失败附加状态 0退换货时 1中止发货时")
    private Integer failureStatus;
    @ApiModelProperty(value = "退货物流公司id")
    private Long logisticsCompanyId;
    @ApiModelProperty(value = "退货物流公司名称")
    private String logisticsCompanyName;
    @ApiModelProperty(value = "退货物流单号")
    private String logisticsNo;

    @ApiModelProperty(value = "逻辑仓出库明细")
    private List<WarehouseWarrantDetailDTO> detailList;
}
