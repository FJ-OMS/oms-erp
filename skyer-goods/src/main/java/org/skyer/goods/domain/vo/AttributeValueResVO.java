package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @description
 * @Author wusc
 * @create 2021/7/6 5:29 下午
 */
@Data
@ApiModel("属性值返回参数")
public class AttributeValueResVO {

    @ApiModelProperty("主键")
    @Encrypt
    private Long id;

    @ApiModelProperty("属性id")
    @Encrypt
    private Long attributeId;

    @ApiModelProperty("属性值")
    private String value;

    @ApiModelProperty("排序")
    private Integer sort;
}
