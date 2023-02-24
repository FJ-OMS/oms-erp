package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailVO {
    @ApiModelProperty(value = "skuCode")
    private String skuCode;

    @ApiModelProperty(value = "订单商品明细平铺ID集合")
    private List<OrderGoodsLineVO> orderGoodsLineIds;
}
