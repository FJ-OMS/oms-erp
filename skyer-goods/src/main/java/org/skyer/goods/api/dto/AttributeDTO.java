package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotNull;

/**
 * @description sku属性
 * @Author zhanglongbing@ebc.com
 * @create 2021/12/27
 */
@Data
public class AttributeDTO {

    @ApiModelProperty("attributeId")
    @Encrypt
    private Long attributeId;

    @ApiModelProperty("类型")
    private Integer attributeType;

    @ApiModelProperty("属性名称")
    private String attributeName;

    @ApiModelProperty("属性值")
    private String attributeValue;

}
