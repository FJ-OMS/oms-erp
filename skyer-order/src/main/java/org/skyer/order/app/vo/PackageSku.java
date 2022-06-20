package org.skyer.order.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PackageSku {

    @ApiModelProperty(value = "sku")
    private String sku;

    @ApiModelProperty(value = "spu")
    private String spu;

    @ApiModelProperty(value = "商品名称")
    private String spuName;

    @ApiModelProperty(value = "数量")
    private Integer number;

}
