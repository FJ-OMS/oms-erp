package org.skyer.afterSales.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageLogisticsCompanyDTO {

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "物流商名称")
    private String name;

    @ApiModelProperty(value = "启用标志")
    private Boolean enableFlag;
}
