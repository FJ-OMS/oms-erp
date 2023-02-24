package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

/**
 * 商品属性选择列表
 * @description
 * @Author wusc
 * @create 2021/7/19 9:44 上午
 */
@Data
public class CheckGoodsAttributeResVO {

    @ApiModelProperty("商品属性")
    @Encrypt
    private Long attributeId;

    @ApiModelProperty("属性类别类别 1单选2复选3文本4下拉")
    private Integer type;

    @ApiModelProperty("属性名称")
    private String attributeName;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("属性值")
    private List<AttributeValueResVO> attributeValueResVOS;

    @ApiModelProperty("选择的属性值id")
    private List<Long> checkValueIdList;

    @ApiModelProperty("为文本框时手写属性值")
    private String valueText;
}
