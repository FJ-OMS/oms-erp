package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @description
 * @Author wusc
 * @create 2021/8/5 4:02 下午
 */
@Data
public class StockEsDTO {

    @ApiModelProperty("仓库id")
    private Long warehouseId;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("仓库类别编码")
    private String warehouseTypeCode;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("商品编码")
    private String spuCode;

    @ApiModelProperty("商品名称")
    private String spuName;

    @ApiModelProperty("商品类型")
    private Integer spuType;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("在库库存")
    private Double stockNum;

    @ApiModelProperty("库存是否超过0")
    private Boolean exceedZero;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("品牌id")
    @Encrypt
    private Long brandId;

}
