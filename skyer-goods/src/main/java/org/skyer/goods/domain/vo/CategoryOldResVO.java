package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @description
 * @Author wusc
 * @create 2021/6/24 9:16 上午
 */
@Data
@ApiModel(description = "根据上级目录获取子级")
public class CategoryOldResVO {

    @ApiModelProperty("主键")
    @Encrypt
    private Long id;

    @ApiModelProperty("分类编码")
    private String categoryCode;


    @ApiModelProperty("分类名称")
    private String categoryName;


    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("类目等级")
    private Integer level;

    @ApiModelProperty("父级id")
    private String frontId;
}
