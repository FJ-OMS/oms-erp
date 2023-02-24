package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xurongfa 2022-04-24 16:28
 */
@Data
public class StatsStockoutDTO {
    @ApiModelProperty("商品名称/编码")
    private String querySpu;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku编码集合")
    private List<String> skuCodeList;

    @ApiModelProperty("spu编码集合")
    private List<String> spuCodeList;
}
