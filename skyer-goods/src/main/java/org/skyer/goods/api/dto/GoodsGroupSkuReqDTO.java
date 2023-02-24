package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.math.BigDecimal;

/**
 * skuId 数量
 * @description
 * @Author wusc
 * @create 2021/8/13 10:43 上午
 */
@ApiModel("商品组合sku")
@Data
public class GoodsGroupSkuReqDTO {
    @ApiModelProperty("skuId")
    @Encrypt
    private Long skuId;

    @ApiModelProperty("数量")
    private BigDecimal num;
}
