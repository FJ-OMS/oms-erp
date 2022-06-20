package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xurongfa 2022-04-27 16:48
 */
@Data
public class WarehouseWarrantInfoLogisticsVO {
    @ApiModelProperty(value = "退货物流公司名称")
    private String logisticsCompanyName;
    @ApiModelProperty(value = "退货物流单号")
    private String logisticsNo;
}
