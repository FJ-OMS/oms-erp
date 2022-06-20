package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.math.BigDecimal;

/**
 * @description
 * @Author wusc
 * @create 2021/8/9 1:38 下午
 */
@Data
@ApiModel(description = "数据同步商品请求参数")
public class AdjustDetailsReqDTO {

    @ApiModelProperty("商品sku")
    private String skuCode;

    @ApiModelProperty("调整类型 1增加 2减少")
    private Integer type;

    @ApiModelProperty("调整数量")
    private BigDecimal num;

    @JsonIgnore
    private Long tenantId;
}
