package org.skyer.goods.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.goods.domain.entity.Category;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

@Data
@ApiModel("商品分类表")
public class CategoryTreeVO {
    @Encrypt
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "分类编码")
    private String code;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @Encrypt
    @ApiModelProperty(value = "父id 默认0,代表一级")
    private Long parentId;

    @ApiModelProperty(value = "状态 true启用 false禁用")
    private Boolean statusFlag;

    @ApiModelProperty(value = "类目等级 一级开始:1")
    private Integer level;

    @ApiModelProperty(value = "是否叶子类目 false否 true是")
    private Boolean subFlag;

    @ApiModelProperty(value = "排序")
    private Long sort;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "下级分类")
    private List<CategoryTreeVO> children;

    @ApiModelProperty(value = "版本号")
    private Long objectVersionNumber;
}
