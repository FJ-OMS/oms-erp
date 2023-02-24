package org.skyer.goods.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

@Data
@ApiModel("商品分类表（包含属性、属性值）")
public class CategoryVO {
    @Encrypt
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "分类编码")
    private String code;


    @ApiModelProperty(value = "分类名称")
    private String name;

    @Encrypt
    @ApiModelProperty(value = "父id 0,代表一级")
    private Long parentId;

    @ApiModelProperty(value = "父级分类名称")
    private String parentName;

    @ApiModelProperty(value = "状态 true启用 false禁用")
    private Boolean statusFlag;

    @ApiModelProperty(value = "类目等级 一级开始")
    private Integer level;

    @ApiModelProperty(value = "是否叶子类目 false否 true是")
    private Boolean subFlag;

    @ApiModelProperty(value = "排序")
    private Long sort;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "商品属性表（包含属性值）")
    private List<CategoryAttributeVO> attributeList;

    @ApiModelProperty(value = "版本号")
    private Long objectVersionNumber;

    @ApiModelProperty(value = "普通分类是否有下级或者叶子节点是否有商品")
    private Boolean hasSubOrSPUFlag;
}
