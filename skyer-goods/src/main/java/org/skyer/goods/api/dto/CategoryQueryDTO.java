package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.starter.keyencrypt.core.Encrypt;


@Data
@ApiModel("商品分类查询")
public class CategoryQueryDTO extends PageRequest {
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

    @ApiModelProperty(value = "状态 true启用 false禁用")
    private Boolean statusFlag;

    @ApiModelProperty(value = "类目等级")
    private Integer level;

    @ApiModelProperty(value = "是否叶子类目 true是 false否")
    private Boolean subFlag;

    @ApiModelProperty(value = "备注")
    private String remark;
}
