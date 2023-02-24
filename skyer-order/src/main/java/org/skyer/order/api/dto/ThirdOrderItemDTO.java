package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 三方创建订单商品详情
 * @author  zlb
 */
@Data
public class ThirdOrderItemDTO {


    @Encrypt
    @ApiModelProperty("skuId")
    @NotNull(message = "skuId不能为空")
    private Long skuId;

    @ApiModelProperty(value = "商品编码")
    private String spuCode;

    @ApiModelProperty(value = "商品名称")
    private String spuName;

    @ApiModelProperty(value = "sku名称")
    private String skuName;

    @ApiModelProperty(value = "商品类型1.实物2虚拟")
    private Integer spuType;

    @ApiModelProperty("sku编码")
    @NotBlank(message = "sku编码不能为空")
    private String skuCode;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("外部销售总价")
    private BigDecimal outerSaleAmount;

    @ApiModelProperty("外部销售单价")
    private BigDecimal outerSingleSaleAmount;

    @ApiModelProperty("内部销售总价")
    private BigDecimal innerSaleAmount;

    @ApiModelProperty("内部销售单价")
    private BigDecimal innerSingleSaleAmount;

    @ApiModelProperty("外部均摊价格")
    private BigDecimal outBalancePrice;

    @ApiModelProperty("内部均摊价格")
    private BigDecimal innerBalancePrice;

    @ApiModelProperty("运费")
    private BigDecimal transPrice;

    @ApiModelProperty("是否为套餐")
    private Integer setMealFlag;

    @ApiModelProperty("赠品标识：0-卖品，1-赠品")
    private Integer giftFlag;

    @ApiModelProperty(value = "套餐标识：0-单品，1-套餐")
    private Integer setFlag;

    @ApiModelProperty(value = "子母件标识：0-普通单品，1-子母件")
    private Integer multiFlag;

    @ApiModelProperty("主商品id")
    private String mainOrderItemCode;

}
