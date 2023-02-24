package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.Date;

/**
 * @author  zlb
 */
@Data

public class ThirdOrderInfoVO {
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "流转状态")
    private String circulationStatus;

    @ApiModelProperty(value = "流转失败原因")
    private String failureReason;

    @ApiModelProperty(value = "平台收货状态 0：未收货 1：已收货")
    private String receivingStatus;

    @ApiModelProperty(value = "业务类型")
    private String businessType;

    @ApiModelProperty(value = "外部订单号")
    private String outerOrderNo;

    @ApiModelProperty(value = "内部订单号")
    private String innerOrderNo;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty("渠道类型编码")
    @LovValue(lovCode = "SCHL.CHANNEL_TYPE", meaningField = "channelTypeMeaning")
    private String channel;

    @ApiModelProperty("渠道类型")
    private String channelTypeMeaning;

    @ApiModelProperty(value = "订单下单时间")
    private Date orderTime;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "创建时间")
    private Date creationDate;

    @ApiModelProperty(value = "买家备注")
    private String buyerRemarks;

    @ApiModelProperty(value = "客服备注")
    private String serviceRemarks;

    @ApiModelProperty(value = "商家/订单备注")
    private String orderRemarks;

    @ApiModelProperty(value = "买家昵称")
    private String buyerNick;

    @ApiModelProperty(value = "提货店铺Id")
    @Encrypt
    private Long takingDeliveryStoreId;

    @ApiModelProperty(value = "提货店铺名称")
    private String takingDeliveryStoreName;

    @ApiModelProperty(value = "销售员工")
    private String salesEmployeeRealName;

    @ApiModelProperty(value = "提货方式")
    private String takingDeliveryWay;

    @ApiModelProperty(value = "员工ID")
    @Encrypt
    private Long salesEmployeeId;

    @ApiModelProperty(value = "订单状态")
    private String orderStatus;
}
