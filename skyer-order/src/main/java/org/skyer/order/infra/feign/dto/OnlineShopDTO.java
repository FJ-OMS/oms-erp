package org.skyer.order.infra.feign.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @description
 * @Author wusc
 * @create 2022/1/18 4:10 下午
 */
@Data
public class OnlineShopDTO {

    @ApiModelProperty("表ID，主键，供其他表做外键")
    @Id
    @Encrypt
    private Long id;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("网店名称")
    @NotNull
    private String name;

    @ApiModelProperty("状态 true禁用")
    private Boolean status;
}
