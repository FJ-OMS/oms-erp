package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 订单创建dto
 * @description
 * @Author wusc
 * @create 2022/1/18 1:18 下午
 */
@Data
public class OrderTableDTO {

    @ApiModelProperty("订单基础信息")
    private OrderBaseInfoDTO orderBaseInfoDTO;

    @ApiModelProperty("订单买家信息")
    private OrderBuyerInfoDTO orderBuyerInfoDTO;

    @ApiModelProperty("订单支付信息")
    private OrderPaymentInfoDTO orderPaymentInfoDTO;

    @ApiModelProperty("订单商品详情")
    @NotEmpty
    private List<OrderItemDTO> orderItemDTOList;
}
