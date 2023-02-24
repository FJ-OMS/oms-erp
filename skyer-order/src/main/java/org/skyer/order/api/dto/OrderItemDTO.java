package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 创建订单详情
 * @description
 * @Author wusc
 * @create 2022/1/18 2:21 下午
 */
@Data
public class OrderItemDTO {

    @ApiModelProperty("后端内部使用唯一值")
    private String orderItemCode;

    @Encrypt
    @ApiModelProperty("skuId")
    @NotNull(message = "skuId不能为空")
    private Long skuId;

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

    @ApiModelProperty("是否为套餐")
    private Integer setMealFlag;

    @ApiModelProperty("1赠品 2随品")
    private Integer giftFlag;

    @ApiModelProperty("主商品id")
    private String mainOrderItemCode;



    @ApiModelProperty("运费")
    private BigDecimal transPrice;

    @ApiModelProperty(value = "是否系统匹配营销赠品")
    private Boolean systemPickFlag;

    private Long activityId;

}
