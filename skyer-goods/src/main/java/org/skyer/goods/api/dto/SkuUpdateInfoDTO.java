package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.goods.domain.entity.SkuAttributeValue;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhanglongbing
 */
@Data
public class SkuUpdateInfoDTO {

    @ApiModelProperty("id")
    @Encrypt
    @NotNull(message = "id不能为空")
    private Long id;
    @ApiModelProperty(value = "skuCode")
    private String skuCode;
    @ApiModelProperty(value = "sku名称")
    private String skuName;
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

    @ApiModelProperty("原始属性列表")
    private List<SkuAttributeValue> skuAttributeList;

    @ApiModelProperty("附加属性列表")
    private List<SkuAttributeValue> otherAttributeList;

}
