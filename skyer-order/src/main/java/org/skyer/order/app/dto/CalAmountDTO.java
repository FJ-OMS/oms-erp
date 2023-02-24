package org.skyer.order.app.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CalAmountDTO {

    @ApiModelProperty("商品均摊")
    private List<PackItemAmountDTO> packItemAmountDTOList;

    @ApiModelProperty("外部销售总价")
    private BigDecimal outTotalPackPrice;

    @ApiModelProperty("内部销售总价")
    private BigDecimal innerTotalPackPrice;

    @ApiModelProperty("总金额")
    private BigDecimal totalPrice;

}
