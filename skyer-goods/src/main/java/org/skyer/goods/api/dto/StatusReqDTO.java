package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2021/6/24 10:18 上午
 */
@Data
@ApiModel("用于编辑状态")
public class StatusReqDTO {

    @ApiModelProperty("主键id")
    @NotEmpty(message = "主键id不能为空")
    @Encrypt
    private List<Long> ids;

    @ApiModelProperty("状态")
    private Integer status;

}
