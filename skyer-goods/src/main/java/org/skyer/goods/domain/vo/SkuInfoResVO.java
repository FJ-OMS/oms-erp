package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description
 * @Author wusc
 * @create 2021/6/24 1:54 下午
 */
@Data
@ApiModel("规格价格详情")
public class SkuInfoResVO {

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("sku")
    private String skuCode;

    @ApiModelProperty("素材")
    private String material;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("重量")
    private String weight;

    @ApiModelProperty("体积")
    private String volume;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("成本")
    private BigDecimal costPrice;
}
