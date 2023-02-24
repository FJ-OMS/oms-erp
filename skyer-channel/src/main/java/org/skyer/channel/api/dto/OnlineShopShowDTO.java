package org.skyer.channel.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel("网店查看入参类")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OnlineShopShowDTO {

    @ApiModelProperty("网店名称")
    private String name;

    @ApiModelProperty("网店编码")
    private String code;

    @ApiModelProperty("渠道类型")
    private String channelTypeCode;

    @ApiModelProperty("状态 true启用 false禁用")
    private Boolean status;

    @ApiModelProperty("销售组织名称")
    private String orgName;

    @ApiModelProperty("网店类型")
    private String onlineStoreTypeCode;
}
