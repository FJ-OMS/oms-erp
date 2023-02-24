package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description
 * @Author wusc
 * @create 2022/2/10 4:15 下午
 */
@Data
public class UnfinishedTotalVO {
    @ApiModelProperty(value = "缺货")
    private Integer outStockCount;

    @ApiModelProperty(value = "寻源失败")
    private Integer failSeekCount;

    @ApiModelProperty(value = "待收货")
    private Integer waitSendCount;

    @ApiModelProperty(value = "预占成功待审核")
    private Integer preempAuditCount;

    @ApiModelProperty(value = "寻源成功待审核")
    private Integer seekAuditCount;

    @ApiModelProperty(value = "待收货")
    private Integer waitReceiveCount;
}
