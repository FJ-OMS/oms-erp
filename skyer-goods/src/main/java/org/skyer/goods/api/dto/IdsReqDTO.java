package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2021/6/30 11:20 上午
 */
@Data
@ApiModel("删除id")
public class IdsReqDTO {

    @ApiModelProperty("主键id")
    @Encrypt
    private List<Long> ids;
}
