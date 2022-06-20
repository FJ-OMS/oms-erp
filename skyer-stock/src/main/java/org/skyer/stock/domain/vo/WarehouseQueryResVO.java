package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @description
 * @Author wusc
 * @create 2021/8/9 9:26 上午
 */
@Data
public class WarehouseQueryResVO {

    @ApiModelProperty("id主键")
    @Encrypt
    private Long id;

    @ApiModelProperty("仓库名")
    private String name;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("仓库类别编码")
    private  String typeCode;

    @ApiModelProperty("状态")
    private Boolean status;

    @ApiModelProperty("创建时间")
    private Date creationDate;
}
