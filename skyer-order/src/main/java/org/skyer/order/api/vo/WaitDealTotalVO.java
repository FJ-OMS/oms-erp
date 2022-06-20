package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description
 * @Author wusc
 * @create 2022/2/10 4:16 下午
 */
@Data
public class WaitDealTotalVO {
    @ApiModelProperty(value = "缺货")
    private Integer outStockCount;

    @ApiModelProperty(value = "寻源失败")
    private Integer failSeekCount;

    @ApiModelProperty(value = "预占回写失败")
    private Integer failSyncPreempCount;

    @ApiModelProperty(value = "发货回写失败")
    private Integer failSyncSendCount;

    @ApiModelProperty(value = "待审核")
    private Integer waitAudit;

    @ApiModelProperty(value = "待收货")
    private Integer waitReceiveCount;
}
