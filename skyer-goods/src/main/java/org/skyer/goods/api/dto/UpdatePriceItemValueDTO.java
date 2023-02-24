package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("更新值集")
@Data
public class UpdatePriceItemValueDTO {


    @ApiModelProperty("value编码")
    String value;

    @ApiModelProperty("value含义")
    String meaning;

    @ApiModelProperty("排序标识")
    Integer orderSeq;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("生效标识")
    private Integer enabledFlag;


    //====================

    // @ApiModelProperty("值集值ID")
    // private Long lovValueId;
    //
    // @ApiModelProperty("值集ID")
    // private Long lovId;


}
