package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * 订单基础信息修改
 * @description
 * @Author wusc
 * @create 2022/2/16 1:19 下午
 */
@Data
public class OrderBaseDTO {

    @ApiModelProperty(value = "订单id")
    @Encrypt
    private Long orderId;

    @ApiModelProperty(value = "订单备注")
    private String orderRemarks;

    @ApiModelProperty(value = "买家备注/留言")
    private String buyerRemarks;

    @ApiModelProperty(value = "客服备注")
    private String serviceRemarks;

    @ApiModelProperty(value = "用户昵称")
    private String buyerNick;

    @ApiModelProperty(value = "标签code 多个,分割")
    private String tags;

    @ApiModelProperty(value = "收货人信息")
    private ReceiverInfoDTO receiverInfoDTO;
}
