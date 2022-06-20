package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 商品素材
 * </p>
 *
 * @author chenzz
 * @since 2021-06-23
 */
@Getter
@Setter
@ToString
@ApiModel("商品素材")
public class MaterialListDTO {

    /**
     * 编码
     */
    @ApiModelProperty("编码")
    private String code;

    /**
     * 素材名称
     */
    @ApiModelProperty("素材名称")
    private String materialName;


}
