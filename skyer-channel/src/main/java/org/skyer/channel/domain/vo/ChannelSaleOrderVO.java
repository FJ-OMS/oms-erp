package org.skyer.channel.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.boot.platform.lov.annotation.LovValue;

import java.math.BigDecimal;

@Data
public class ChannelSaleOrderVO {

    @ApiModelProperty(value = "渠道类型code")
    @LovValue(lovCode = "SCHL.CHANNEL_TYPE", meaningField = "channelName")
    private  String channelTypeCode;

    @ApiModelProperty(value ="渠道名称")
    private String channelName;

    @ApiModelProperty(value = "店铺名")
    private String storeName;

    @ApiModelProperty(value="店铺编码")
    private String storeCode;

    @ApiModelProperty(value="所属公司")
    private String organizationName;

    @ApiModelProperty("店铺类型编码")
    private String storeTypeCode;


    @ApiModelProperty(value="店铺类型")
    private String storeType;

    @ApiModelProperty(value="订单数")
    private Integer orderCount;

    @ApiModelProperty(value="销售单数")
    private BigDecimal orderAmount;

    @ApiModelProperty(value="售后单数")
    private Integer reverseOrderCount;

    @ApiModelProperty(value="售后金额")
    private BigDecimal reverseOrderAmount;
}
