package org.skyer.goods.api.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("商品分类树筛选条件")
public class CategoryTreeQueryDTO {
    @ApiModelProperty(value = "状态 true启用 false禁用")
    private Boolean statusFlag;

    @ApiModelProperty(value = "是否叶子类目 true是 false否")
    private Boolean subFlag;

    @ApiModelProperty(value = "是否移除子孙没有叶子分类的普通分类 true移除 false不移除")
    private Boolean removeNotSubFlag;
}
