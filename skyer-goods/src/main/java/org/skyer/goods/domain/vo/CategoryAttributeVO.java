package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;


/**
 * @author xurongfa 2021-12-27 09:41
 */
@Data
@ApiModel("商品分类属性（包含属性值）")
public class CategoryAttributeVO {
//    @Encrypt
//    @ApiModelProperty("id")
//    private Long id;
//
    @Encrypt
    @ApiModelProperty(value = "分类id sgoo_category.id")
    private Long categoryId;

    @ApiModelProperty(value = "属性名称")
    private String name;

    @ApiModelProperty(value = "属性编码")
    private String code;

    @ApiModelProperty(value = "排序")
    private Long sort;

    @ApiModelProperty(value = "表单方式 1单选2复选3文本4下拉")
    private Integer formType;

    @ApiModelProperty(value = "是否用于生成sku true是 false否")
    private Boolean skuFlag;

    @ApiModelProperty(value = "状态 true启用 false禁用")
    private Boolean statusFlag;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "来源类型 0新增 1系统 2父级属性")
    private Integer sourceType;

    @ApiModelProperty(value = "属性值列表")
    private List<CategoryAttributeValueVO> attributeValueList;
}
