package org.skyer.order.infra.feign.entity.generate.odo.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OutboundDeliveryOrderDetailLineDTO {
    @ApiModelProperty(value = "商品SKU编码")
    private String skuCode;

    @ApiModelProperty(value = "商品SKU名称")
    private String skuName;

    @ApiModelProperty(value = "商品编码")
    private String spuCode;

    @ApiModelProperty(value = "商品名称")
    private String spuName;

    @ApiModelProperty(value = "订单商品明细平铺ID集合")
    private List<Long> orderGoodsLineIds;

}
