package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.goods.domain.entity.Sku;


/**
 *
 * @author zhanglb
 */
@Data
public class SkuUpdateDTO {

    @ApiModelProperty(value = "sku更改信息")
    private SkuUpdateInfoDTO sku;

    @ApiModelProperty(value = "true保存为草稿  false提交")
    private Boolean flag;



}
