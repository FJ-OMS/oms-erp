package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WarehouseWarrantLogisticsDTO {
    @ApiModelProperty(value = "退货物流公司id")
    private Long logisticsCompanyId;
    @ApiModelProperty(value = "退货物流公司名称")
    private String logisticsCompanyName;
    @ApiModelProperty(value = "退货物流单号")
    private String logisticsNo;

}
