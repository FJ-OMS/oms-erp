package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @description
 * @Author wusc
 * @create 2022/2/9 5:53 下午
 */
@Data
public class ItemSearchDTO extends PageReqDTO{
    @ApiModelProperty(value = "订单id",required = true)
    @Encrypt
    @NotNull(message = "orderId不能为空")
    private Long orderId;
    @ApiModelProperty(value = "发货状态")
    private String deliveryStatus;

    @ApiModelProperty(value = "预占状态")
    private String preemptionStatus;

    @ApiModelProperty(value = "寻源状态")
    private String sourceStatus;

    @ApiModelProperty(value = "挂起状态")
    private  String suspendStatus;

    @ApiModelProperty(value = "是否缺货")
    private Boolean isOutStock;

    @ApiModelProperty(value = "是否退款")
    private Boolean refundFlag;

    @ApiModelProperty(value = "预占回写状态")
    private String syncPreempStatus;

    @ApiModelProperty(value = "发货回写状态")
    private String syncDeliveryStatus;

    @ApiModelProperty(value = "收货状态")
    private String receiveStatus;

}
