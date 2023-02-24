package org.skyer.order.api.es;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xurongfa 2022-04-28 11:40
 */
@Data
public class SpuEsModel {
    @ApiModelProperty("spu主键")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "spu")
    private String spuCode;

    @ApiModelProperty(value = "类型1.实物2虚拟")
    private Integer type;

    @ApiModelProperty("套餐标识：0-单品，1-套餐")
    private Integer setFlag;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

}
