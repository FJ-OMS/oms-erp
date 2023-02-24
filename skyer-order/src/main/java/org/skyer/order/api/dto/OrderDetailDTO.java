package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDetailDTO {

    @ApiModelProperty(value = "skuCode")
    private String skuCode;

    @ApiModelProperty(value = "skuID")
    private Long skuId;

    @ApiModelProperty(value = "下单商品数量(订单数量)")
    private BigDecimal orderQty;

    @ApiModelProperty(value = "订单商品明细平铺ID集合")
    private List<Long> orderGoodsLineIds;
}
