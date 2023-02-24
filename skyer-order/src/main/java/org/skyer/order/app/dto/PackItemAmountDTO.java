package org.skyer.order.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PackItemAmountDTO {

    @ApiModelProperty("商品明细id")
    private Long itemId;

    @ApiModelProperty("均摊编码")
    private String code;

    @ApiModelProperty("打包后的均摊总价")
    private BigDecimal packBalancePrice;

    @ApiModelProperty("外部销售总价")
    private BigDecimal outTotalSplitPrice;

    @ApiModelProperty("内部销售总价")
    private BigDecimal innerTotalSplitPrice;

    @ApiModelProperty("需要均摊商品")
    private List<SplitItemAmountDTO> splitItemAmountDTOList;
}
