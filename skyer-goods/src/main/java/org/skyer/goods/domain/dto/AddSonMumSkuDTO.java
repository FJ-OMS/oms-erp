package org.skyer.goods.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddSonMumSkuDTO {

    @ApiModelProperty(value = "子商品sku_code",required = true)
    @NotBlank
    private String sonSkuCode;

    @ApiModelProperty(value = "数量",required = true)
    @NotNull
    private Long amount;
}
