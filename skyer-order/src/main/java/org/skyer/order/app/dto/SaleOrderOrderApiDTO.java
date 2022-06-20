package org.skyer.order.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SaleOrderOrderApiDTO {

    @ApiModelProperty("订单开始时间")
    private Date startDate;

    @ApiModelProperty("订单结束时间")
    private Date endDate;

    @ApiModelProperty("店铺code")
    private List<String> storeCode;
}
