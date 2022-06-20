package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

@Data
@ApiModel(value ="商品分类属性查询对象")
public class CategoryAttributeQueryDTO {
    @Encrypt
    @ApiModelProperty("分类属性id")
    private Long id;

    @Encrypt
    @ApiModelProperty(value = "分类id,null返回空集", required = true)
    private Long categoryId;

    @ApiModelProperty(value = "表单方式 1单选2复选3文本4下拉")
    private Integer formType;

    @ApiModelProperty(value = "是否用于生成sku true是 false否")
    private Boolean skuFlag;

    @ApiModelProperty(value = "状态 true启用 false禁用")
    private Boolean statusFlag;

    @ApiModelProperty(value = "来源类型 0新增 1系统 2分级分类")
    private Integer sourceType;
}
