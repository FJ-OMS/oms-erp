package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单服务远程调用
 * @description
 * @Author zhanglb
 * @create 2022/01/18
 */
@Data
public class RpcOrderResVO {

    @ApiModelProperty("商品Id")
    private Long spuId;

    @ApiModelProperty("商品code")
    private String spuCode;

    @ApiModelProperty("商品名称")
    private String spuName;

    @ApiModelProperty("商品类型")
    private String spuType;

    @ApiModelProperty("skuId")
    private Long skuId;

    @ApiModelProperty("skuCode")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("品牌数据")
    private String brandInfo;

    @ApiModelProperty("素材数据")
    private String materialInfo;

    @ApiModelProperty("类型数据")
    private String categoryInfo;

    @ApiModelProperty("属性数据")
    private String attributeInfo;


    @ApiModelProperty(value = "成本价")
    private BigDecimal costPrice;
    @ApiModelProperty(value = "销售价")
    private BigDecimal salesPrice;
    @ApiModelProperty(value = "活动价")
    private BigDecimal activityPrice;
    @ApiModelProperty(value = "市场价")
    private BigDecimal marketPrice;
    @ApiModelProperty(value = "分销价")
    private BigDecimal distributionPrice;
    @ApiModelProperty(value = "批发价")
    private BigDecimal wholesalePrice;
    @ApiModelProperty(value = "结算价")
    private BigDecimal clearingPrice;
    @ApiModelProperty(value = "商品重量")
    private BigDecimal weight;
    @ApiModelProperty(value = "商品长度")
    private BigDecimal length;
    @ApiModelProperty(value = "商品宽度")
    private BigDecimal width;
    @ApiModelProperty(value = "商品高度")
    private BigDecimal height;

    @ApiModelProperty("套餐标识：0-单品，1-套餐")
    private Integer setFlag;
    @ApiModelProperty("赠品标识：0-卖品，1-赠品")
    private Integer giftFlag;
    @ApiModelProperty("子母件标识：0-普通单品，1-子母件")
    private Integer multiFlag;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("套餐主商品的sku")
    private String mainSkuCode;

}
