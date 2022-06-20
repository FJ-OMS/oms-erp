package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * 仓库通用查询
 * @description
 * @Author wusc
 * @create 2021/8/9 9:22 上午
 */
@Data
public class WarehouseQueryReqDTO extends PageReqDTO{

    @ApiModelProperty("仓库名")
    private String name;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("id")
    @Encrypt
    private Long id;

    @ApiModelProperty("状态 1启用 0禁用")
    private Integer status;

    @ApiModelProperty("类型 STOCK.WAREHOUSE_TYPE")
    private List<String> typeCode;
}
