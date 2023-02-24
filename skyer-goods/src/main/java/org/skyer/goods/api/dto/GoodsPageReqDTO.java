package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2021/6/24 11:33 上午
 */
@Data
public class GoodsPageReqDTO extends PageReqDTO{

    @ApiModelProperty("主键id")
    @Encrypt
    private String id;


    @ApiModelProperty("spu编码")
    private String spuCode;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品类别")
    private Integer type;

    @ApiModelProperty("目录编码")
    @Encrypt
    private List<Long> categoryIdList;

    @ApiModelProperty(value = "状态")
    private Integer status;


    @ApiModelProperty("套餐标识：0-单品，1-套餐")
    private Integer setFlag;


}
