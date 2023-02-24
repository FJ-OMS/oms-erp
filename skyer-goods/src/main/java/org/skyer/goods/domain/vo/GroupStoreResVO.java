package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.boot.platform.lov.annotation.LovValue;

/**
 * 店铺类型
 * @description
 * @Author wusc
 * @create 2021/8/16 9:43 上午
 */
@Data
public class GroupStoreResVO {

    @ApiModelProperty(value = "店铺id")
    private Long storeId;

    @ApiModelProperty("店铺code")
    private String code;

    @ApiModelProperty("店铺名称")
    private String name;

    @ApiModelProperty("类型编码, 值集 SCHL.CHANNEL_TYP")
    @LovValue(lovCode = "SCHL.CHANNEL_TYPE",meaningField = "channelTypeMeaning")
    private String channelTypeMeaning;

    @ApiModelProperty("网店类型编码 SCHL.SHOP_TYPE")
    @LovValue(lovCode = "SCHL.SHOP_TYPE", meaningField = "onlineStoreTypeMeaning")
    private String onlineStoreTypeMeaning;
}
