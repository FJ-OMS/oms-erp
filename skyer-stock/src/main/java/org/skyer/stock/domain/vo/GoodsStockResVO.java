package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @description
 * @Author wusc
 * @create 2021/8/6 2:26 下午
 */
@Data
public class GoodsStockResVO {

    @ApiModelProperty("商品编码")
    private String spuCode;

    @ApiModelProperty("商品名称")
    private String spuName;

    @ApiModelProperty("商品类型（1.实物2.虚拟）")
    private Integer spuType;

    @ApiModelProperty("sku编码名称")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("仓库id")
    @Encrypt
    private Long warehouseId;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "在库库存")
    private BigDecimal stockNum;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("仓库类型")
    private String typeCode;

    @ApiModelProperty("品牌")
    private String brandName;

    @ApiModelProperty("品牌Id")
    @Encrypt
    private Long brandId;
}
