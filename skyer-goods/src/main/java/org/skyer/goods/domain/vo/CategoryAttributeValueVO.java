package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("商品分类属性值")
public class CategoryAttributeValueVO {
    @ApiModelProperty(value = "属性值")
    private String value;

    @ApiModelProperty(value = "排序")
    private Long sort;

}
