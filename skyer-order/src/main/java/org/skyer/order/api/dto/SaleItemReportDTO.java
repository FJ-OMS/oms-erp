package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SaleItemReportDTO {

    @ApiModelProperty(value = "开始时间")
    public Date startDate;

    @ApiModelProperty(value = "结束时间")
    public Date endDate;

    @ApiModelProperty(value = "商品名称/编码")
    public String itemCodeName;

    @ApiModelProperty(value = "渠道")
    private String channel;

}
