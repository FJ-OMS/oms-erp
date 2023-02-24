package org.skyer.goods.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageSpuInstallServiceDTO {

    private String spuCode;

    private String spuName;

    @ApiModelProperty(value = "安装服务编码")
    private String installServiceCode;

    @ApiModelProperty(value = "安装服务名称")
    private String installServiceName;

}
