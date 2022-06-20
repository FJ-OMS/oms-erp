package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2021/8/13 10:11 上午
 */
@ApiModel("商品组合")
@Data
public class GoodsGroupReqDTO {

    @ApiModelProperty("id")
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "spuId")
    @Encrypt
    private Long spuId;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("套餐名")
    private String groupName;


    @ApiModelProperty("店铺id")
    @Encrypt
    private List<Long> storeIdList;

    @ApiModelProperty("是否全部店铺")
    private Boolean isAllStore;

    @ApiModelProperty("组合商品sku")
    private List<GoodsGroupSkuReqDTO> goodsGroupSkuReqDTOS;

}
