package org.skyer.order.infra.feign.entity.preemption.exe.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PreemptionDTO {
    @ApiModelProperty(value = "订单编号(内部)")
    private String orderCode;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "网店名称")
    private String shopName;

    @ApiModelProperty(value = "网店编码")
    private String shopCode;
    //
    // @ApiModelProperty(value = "渠道类型")
    // private String channelTypeCode;

    @ApiModelProperty(value = "下单时间")
    private Date orderCreationDate;

    @ApiModelProperty(value = "平台订单号(第三方)")
    private String platformOrderCode;

    private List<DetailListData> detailList;
}
