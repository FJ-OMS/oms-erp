package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

/**
 * 查询该商品属性及其对应的属性值
 * @description
 * @Author wusc
 * @create 2021/7/7 9:30 上午
 */
@Data
public class AttributeAndValueResVO {

    /**
     * 属性值id
     */
    @ApiModelProperty("属性值id")
    @Encrypt
    private Long attributeId;

    /**
     * 属性名
     */
    @ApiModelProperty("属性名")
    private String attributeName;

    /**
     * 属性类别
     */
    @ApiModelProperty("属性类别 1单选2复选3文本4下拉")
    private Integer type;

    /**
     * 是否用于生成sku
     */
    @ApiModelProperty("是否用于生成sku")
    private Boolean isSku;

    /**
     * 属性编码
     */
    @ApiModelProperty("属性编码")
    private String code;
    /**
     * 属性值
     */
    @ApiModelProperty("属性值")
    private List<AttributeValueResVO> attributeValueResVOList;
}
