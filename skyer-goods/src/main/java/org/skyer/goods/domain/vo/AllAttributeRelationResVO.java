package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @description
 * @Author wusc
 * @create 2021/7/16 10:29 上午
 */
@Data
@ApiModel("属性返回参数")
public class AllAttributeRelationResVO {

    @ApiModelProperty("分类或者商品id")
    @Encrypt
    private Long mainId;

    @ApiModelProperty("属性id")
    private Long attributeId;

    @ApiModelProperty("属性编码")
    private String code;

    @ApiModelProperty("属性名称")
    private String attributeName;

    @ApiModelProperty("类别 1单选2复选3文本4下拉")
    private Integer type;

    @ApiModelProperty("属性是否属于自己")
    private Boolean isSelf;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("属性关联id")
    @Encrypt
    private Long id;
}
