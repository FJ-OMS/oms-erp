package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xurongfa 2022-04-26 9:59
 */
@Data
public class WarehouseWarrantQtyVO {
    @ApiModelProperty("已入库数量")
    private BigDecimal alreadyTotalQty;
    @ApiModelProperty("入库数量")
    private BigDecimal totalQty;
}
