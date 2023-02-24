package org.skyer.goods.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageInstallServiceDTO {

    @ApiModelProperty(value = "安装服务编码")
    private String serviceCode;

    @ApiModelProperty(value = "服务名称")
    private String name;

}
