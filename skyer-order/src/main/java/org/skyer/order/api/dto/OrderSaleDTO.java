package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OrderSaleDTO {

    @ApiModelProperty("开始时间")
    private Date startDate;

    @ApiModelProperty("结束时间")
    private Date endDate;

    @ApiModelProperty("时间类型")
    private Integer dateType;

    @ApiModelProperty("渠道")
    private String channel;


    private Integer type;
}
