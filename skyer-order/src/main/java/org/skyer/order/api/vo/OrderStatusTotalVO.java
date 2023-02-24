package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description
 * @Author wusc
 * @create 2022/2/10 4:15 下午
 */
@Data
public class OrderStatusTotalVO {
    @ApiModelProperty(value = "完成数量")
    private Integer finishCount;

    @ApiModelProperty(value = "未完成数量")
    private Integer unfinishedCount;

    @ApiModelProperty(value = "取消数量")
    private Integer cancelCount;
}
