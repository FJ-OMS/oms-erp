package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

@Data
public class OrderCancelDTO {

    /**
     * 内部订单号
     */
    @ApiModelProperty(value = "内部订单号")
    private String innerOrderNo;

    /**
     * itemId
     */
    @Encrypt
    @ApiModelProperty(value = "商品id列表")
    private List<Long> itemIdList;

    /**
     * 商品行id列表
     */
    @ApiModelProperty(value = "商品行id列表")
    private List<Long> itemLineIdList;

    @ApiModelProperty(value = "取消原因")
    private String cancelReason;
}
