package org.skyer.channel.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("门店设置入参类")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhysicalShopShowDTO {
    @ApiModelProperty("网店名称")
    private String name;

    @ApiModelProperty("状态 true启用 false禁用")
    private Boolean channelWarehouseStatus;

    @ApiModelProperty("销售组织名称")
    private String orgName;

    @ApiModelProperty("门店类型")
    private String typeCode;

    @ApiModelProperty("o2o自提门店")
    private Boolean selfPickupFlag;

    @ApiModelProperty("负责人")
    private String manager;
}
