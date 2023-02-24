package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zlb
 */
@Data
public class SkuPackageVO {
    @ApiModelProperty("id")
    @Encrypt
    private Long id;
    @ApiModelProperty(value = "商品名称")
    private String spuName;
    @ApiModelProperty(value = "spuCode")
    private String spuCode;
    @ApiModelProperty(value = "skuCode")
    private String skuCode;
    @ApiModelProperty(value = "sku名称")
    private String skuName;
    @ApiModelProperty(value = "成本价")
    private BigDecimal costPrice;
    @ApiModelProperty(value = "销售价")
    private BigDecimal salesPrice;
    @ApiModelProperty(value = "套餐内sku数量")
    private Long num;
}
