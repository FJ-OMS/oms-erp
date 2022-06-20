package org.skyer.goods.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SkuBatchSaveDTO {
    @ApiModelProperty(value = "SKU列表")
    private List<SkuListRequestDTO> skuList;

    @ApiModelProperty(value = "true保存为草稿  false提交")
    private Boolean flag;

    @ApiModelProperty("套餐标识：0-单品，1-套餐")
    private Integer setFlag;
}
