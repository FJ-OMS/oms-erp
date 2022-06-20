package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.math.BigDecimal;

/**
 * @description
 * @Author wusc
 * @create 2022/2/9 5:50 下午
 */
@Data
public class OrderItemLinePageVO {


    @ApiModelProperty(value = "商品行id")
    @Encrypt
    private Long id;
    @ApiModelProperty(value = "商品名称")
    private String spuName;

    @ApiModelProperty(value = "商品code")
    private String spuCode;

    @ApiModelProperty(value = "skuCode")
    private String skuCode;

    @ApiModelProperty(value = "是否赠品")
    private Integer giftFlag;

    @ApiModelProperty(value = "是否退款")
    private Integer refundFlag;

    @ApiModelProperty(value = "数量")
    private Integer quantity;

    @ApiModelProperty(value = "第三方销售单价")
    private BigDecimal thirdSaleAmount;

    @ApiModelProperty("三方均摊总价格")
    private BigDecimal outBalancePrice;

    @ApiModelProperty(value = "内部销售价")
    private BigDecimal innerSalePrice;

    @ApiModelProperty("内部均摊价格")
    private BigDecimal innerBalancePrice;

    @ApiModelProperty(value = "成本")
    private BigDecimal costAmount;

    @ApiModelProperty(value = "成本总价")
    private BigDecimal totalCostAmount;

    @ApiModelProperty("运费")
    private BigDecimal transPrice;

    @ApiModelProperty(value = "发货状态")
    private String deliveryStatus;

    @ApiModelProperty(value = "预占状态")
    private String preemptionStatus;

    @ApiModelProperty(value = "寻源状态")
    private String sourceStatus;


    @ApiModelProperty(value = "聚合商品行，预占后渠道仓数量同步平台状态")
    private String syncPreempStatus;

    @ApiModelProperty(value = "聚合商品行，发（收）货同步平台状态")
    private String syncDeliveryStatus;

    @ApiModelProperty(value = "订单级-挂起状态")
    private  String suspendStatus;

    @ApiModelProperty(value = "当前状态")
    private String currentStatus;

    @ApiModelProperty(value = "收货状态")
    private String receivingStatus;

    @ApiModelProperty(value = "标签 多个用逗号隔开")
    private String tags;
}
