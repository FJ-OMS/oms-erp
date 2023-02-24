package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xurongfa 2022-03-08 15:14
 */
@Data
@ApiModel("店铺id集合和sku编码集合")
public class ShopIdsAndSkuCodesDTO {
    @ApiModelProperty("店铺id集合")
    private List<Long> shopIdList;
    @ApiModelProperty("sku编码集合")
    private List<String> skuCodeList;
}
