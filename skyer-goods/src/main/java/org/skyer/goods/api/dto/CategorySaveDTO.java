package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@ApiModel("商品分类保存")
public class CategorySaveDTO {
    @Encrypt
    @ApiModelProperty("id 为空代表新增")
    private Long id;

    @ApiModelProperty(value = "分类编码")
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    private String code;

    @NotNull
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$")
    @ApiModelProperty(value = "分类名称")
    private String name;

    @Encrypt
    @ApiModelProperty(value = "父id 0,代表一级")
    private Long parentId;

    @ApiModelProperty(value = "状态 true启用 false禁用")
    private Boolean statusFlag;

    @ApiModelProperty(value = "类目等级 一级开始 默认1")
    private Integer level;

    @ApiModelProperty(value = "是否叶子类目 false否 true是")
    private Boolean subFlag;

    @ApiModelProperty(value = "排序")
    private Long sort;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "商品属性表（包含属性值）")
    private List<CategoryAttributeSaveDTO> attributeList;

    @ApiModelProperty(value = "版本号")
    private Long objectVersionNumber;
}
