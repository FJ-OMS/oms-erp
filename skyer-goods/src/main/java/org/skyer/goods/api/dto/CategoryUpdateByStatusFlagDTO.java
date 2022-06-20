package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("商品分类修改状态")
public class CategoryUpdateByStatusFlagDTO {
    @Encrypt
    @ApiModelProperty(value = "id", required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "状态 true启用 false禁用", required = true)
    @NotNull
    private Boolean statusFlag;

    @ApiModelProperty(value = "版本号")
    private Long objectVersionNumber;
}
