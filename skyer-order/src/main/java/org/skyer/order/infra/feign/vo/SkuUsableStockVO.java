package org.skyer.order.infra.feign.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xurongfa 2022-04-24 15:18
 */
@Data
public class SkuUsableStockVO {
    @ApiModelProperty(value = "sku编码")
    private String skuCode;

    @ApiModelProperty(value = "可用库存")
    private BigDecimal usableNum;
}
