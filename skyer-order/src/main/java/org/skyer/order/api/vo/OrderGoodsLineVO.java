package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderGoodsLineVO {
    @ApiModelProperty(value = "商品行ID")
    private Long goodsLineId;
    @ApiModelProperty(value = "解占标识 true成功")
    private Boolean rescissionFlag;
    @ApiModelProperty(value = "备注")
    private String remark;
}
