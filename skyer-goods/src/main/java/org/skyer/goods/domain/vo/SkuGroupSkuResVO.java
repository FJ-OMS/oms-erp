package org.skyer.goods.domain.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 *
 * @description
 * @Author wusc
 * @create 2021/8/16 9:45 上午
 */
@Data
public class SkuGroupSkuResVO {

    @ApiModelProperty("skuId")
    private Long skuId;

    @ApiModelProperty("spuCode")
    private String spuCode;

    @ApiModelProperty("spu名称")
    private String spuName;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("数量")
    private BigDecimal num;
}
