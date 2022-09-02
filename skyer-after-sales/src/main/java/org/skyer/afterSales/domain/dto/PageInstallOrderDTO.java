package org.skyer.afterSales.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageInstallOrderDTO {

    @ApiModelProperty(value = "安装单号,订单号等")
    private String orderNo;

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "渠道名称")
    private String channelName;

}
