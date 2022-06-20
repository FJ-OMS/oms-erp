package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @description
 * @Author wusc
 * @create 2021/8/10 9:59 上午
 */
@Data
@ApiModel(description = "用于审核")
public class AuditReqDTO {

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Encrypt
    private Long id;

    @ApiModelProperty("审核状态 STOCK.RECORD_STATUS")
    @LovValue("STOCK.RECORD_STATUS")
    private String status;

    @ApiModelProperty("值集 STOCK.RECORD_TYPE")
    private String typeCode;
}
