package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author xurongfa 2022-02-23 16:09
 */
@Data
@ApiModel("出库结果单")
public class ResultRecordListVO {
    @ApiModelProperty("主键")
    @Encrypt
    private Long id;
    @ApiModelProperty(value = "结果单编码")
    private String resultCode;
    @ApiModelProperty(value = "出库通知单id(sstock_outbound_delivery_order.id)")
    @Encrypt
    private Long odoId;
    @ApiModelProperty(value = "出库通知单号")
    private String odoCode;
    @ApiModelProperty(value = "出库类型编码")
    @LovValue(lovCode = "STOCK.OUT_TYPE",meaningField = "outTypeMeaning")
    private String outType;
    @ApiModelProperty(value = "出库类型名称")
    private String outTypeMeaning;
    @ApiModelProperty(value = "状态 0全部出库")
    private Long status;
    @ApiModelProperty(value = "已发货数量")
    private Integer alreadyOutQty;
    @ApiModelProperty(value = "发货逻辑仓ID")
    @Encrypt
    private Long outLogicWarehouseId;
    @ApiModelProperty(value = "发货逻辑仓编码")
    private String outLogicWarehouseCode;
    @ApiModelProperty(value = "发货逻辑仓名称")
    private String outLogicWarehouseName;
    @ApiModelProperty(value = "店铺ID")
    @Encrypt
    private Long shopId;
    @ApiModelProperty(value = "店铺编码")
    private String shopCode;
    @ApiModelProperty(value = "店铺名称")
    private String shopName;
    @ApiModelProperty(value = "渠道类型")
    @LovValue(lovCode = "SCHL.CHANNEL_TYPE",meaningField = "channelTypeCodeMeaning")
    private String channelTypeCode;
    @ApiModelProperty(value = "渠道类型名称")
    private String channelTypeCodeMeaning;
    @ApiModelProperty(value = "创建时间")
    private Date creationDate;

    @ApiModelProperty(value = "订单id")
    private Long orderId;
    @ApiModelProperty(value = "订单编号")
    private String orderCode;

    @ApiModelProperty(value = "wms单号")
    private String wmsCode;
    @ApiModelProperty(value = "运单号")
    private String shippingNo;

    @ApiModelProperty(value = "物流商")
    private String logisticCompany;
    @ApiModelProperty(value = "物流商编号")
    private String logisticCompanyCode;
    @ApiModelProperty(value = "物流商ID")
    @Encrypt
    private Long logisticCompanyId;
    @ApiModelProperty(value = "物流业务")
    private String logisticBusiness;
    @ApiModelProperty(value = "物流业务编码")
    private String logisticBusinessCode;
    @ApiModelProperty(value = "物流业务ID")
    @Encrypt
    private Long logisticBusinessId;
}
