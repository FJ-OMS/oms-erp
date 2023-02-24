package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.goods.domain.entity.SkuPackage;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @description
 * @Author zhanglongbing@ebc.com
 * @create 2021/12/27
 */
@Data
public class SkuListRequestDTO {
    @ApiModelProperty("spuId")
    @Encrypt
    @NotNull(message = "spuId不能为空")
    private Long spuId;

    @ApiModelProperty("skuCode")
    @NotBlank(message = "skuCode不能为空")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @NotBlank(message = "skuName不能为空")
    private String skuName;

    @ApiModelProperty("商品重量")
    private BigDecimal weight;

    @ApiModelProperty("商品长度")
    private BigDecimal length;

    @ApiModelProperty("商品宽度")
    private BigDecimal width;

    @ApiModelProperty("商品高度")
    private BigDecimal height;

    @ApiModelProperty("成本价")
    private BigDecimal costPrice;

    @ApiModelProperty("销售价")
    private BigDecimal salesPrice;

    @ApiModelProperty("活动价")
    private BigDecimal activityPrice;

    @ApiModelProperty("市场价")
    private BigDecimal marketPrice;

    @ApiModelProperty("分销价")
    private BigDecimal distributionPrice;

    @ApiModelProperty("批发价")
    private BigDecimal wholesalePrice;

    @ApiModelProperty("结算价")
    private BigDecimal clearingPrice;

    @ApiModelProperty("原始属性列表")
    private List<AttributeDTO> skuAttributeList;

    @ApiModelProperty("附加属性列表")
    private List<AttributeDTO> otherAttributeList;

    @ApiModelProperty("套餐信息 sku明细")
    @Encrypt
    private List<SkuPackage> skuDetailList;
}
