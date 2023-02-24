package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "生成sku列表vo")
public class CreateSkuListVo {
    // private Long id;

    private String skuCode;

    private String attributeIdText;

    private String attributeValueIdText;

    private String attributeValueText;
}
