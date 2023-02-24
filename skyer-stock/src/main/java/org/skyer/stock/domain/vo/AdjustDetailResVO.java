package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description
 * @Author wusc
 * @create 2021/8/10 11:43 上午
 */
@Data
public class AdjustDetailResVO {

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("商品code")
    private String spuCode;

    @ApiModelProperty("商品名称")
    private String spuName;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("调整类型")
    private Integer type;

    @ApiModelProperty("计划数量")
    private BigDecimal planNum;

    @ApiModelProperty(value = "实际调整数量")
    private BigDecimal realityNum;

    @ApiModelProperty(value = "取消数量")
    private BigDecimal cancelNum;

}
