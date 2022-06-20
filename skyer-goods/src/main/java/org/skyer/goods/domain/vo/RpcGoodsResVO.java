package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * 用于远程调用时候需要值
 * @description
 * @Author wusc
 * @create 2021/8/9 10:30 上午
 */
@Data
public class RpcGoodsResVO {
    @ApiModelProperty("id")
    @Encrypt
    private Long id;

    @ApiModelProperty("商品code")
    private String spuCode;

    @ApiModelProperty("skuCode")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("商品名称")
    private String spuName;

    @ApiModelProperty("商品分类")
    private String categoryStr;

    @ApiModelProperty("最后一级分类id")
    @Encrypt
    private Long lastCategoryId;

    @ApiModelProperty("品牌名")
    private String brandName;

    @ApiModelProperty("品牌ID")
    private Long brandId;

    @ApiModelProperty("商品类型")
    private Integer spuType;

    @ApiModelProperty("套餐标识：0-单品，1-套餐")
    private Integer setFlag;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("套餐主商品的sku")
    private String mainSkuCode;

}
