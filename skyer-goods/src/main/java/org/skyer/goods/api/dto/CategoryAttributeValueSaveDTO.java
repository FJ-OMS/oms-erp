package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分类属性值")
public class CategoryAttributeValueSaveDTO {

//    @Encrypt
//    @ApiModelProperty("id")
//    private Long id;
//
//    @Encrypt
//    @ApiModelProperty(value = "属性id sgoo_category_attribute.id")
//    private Long attributeId;

    @ApiModelProperty(value = "属性值")
    private String value;

    @ApiModelProperty(value = "排序")
    private Long sort;
}
