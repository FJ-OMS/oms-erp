package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description
 * @Author wusc
 * @create 2021/8/16 5:15 下午
 */
@Data
@ApiModel("分组传入参数")
public class GroupPageReqDTO extends PageReqDTO{

    @ApiModelProperty(value = "spu编码")
    private String spuCode;

    @ApiModelProperty("spu名称")
    private String spuName;
}
