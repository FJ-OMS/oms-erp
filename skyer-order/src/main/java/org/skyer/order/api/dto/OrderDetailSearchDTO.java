package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

@Data
public class OrderDetailSearchDTO {

    @ApiModelProperty(value = "orderId")
    @Encrypt
    private Long orderId;
    @ApiModelProperty(value = "orderCode")
    private String orderCode;
}
