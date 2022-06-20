package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xurongfa 2022-04-26 15:42
 */
@Data
public class WarehouseResultQtyVO {
    @ApiModelProperty("已入库数量")
    private BigDecimal alreadyQty;
}
