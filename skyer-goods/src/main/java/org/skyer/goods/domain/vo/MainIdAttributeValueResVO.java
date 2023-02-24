package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

/**
 * 根据mainid查询所有属性或者属性值
 * @description
 * @Author wusc
 * @create 2021/7/7 9:15 上午
 */
@Data
public class MainIdAttributeValueResVO {

    /**
     * 商品或者分类的id
     */
    @ApiModelProperty("主键")
    @Encrypt
    private Long mainId;

    @ApiModelProperty("名称")
    private String name;

    /**
     * 排序级别
     */
    @ApiModelProperty("排序级别")
    private Integer level;

    /**
     * 商品属性对应属性值
     */
    @ApiModelProperty("属性及其对应的属性值")
    private List<AttributeAndValueResVO> attributeAndValueResVOS;

}
