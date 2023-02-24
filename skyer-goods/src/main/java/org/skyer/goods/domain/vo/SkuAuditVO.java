package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.skyer.core.cache.Cacheable;
import org.skyer.goods.domain.entity.Sku;

@Data
public class SkuAuditVO implements Cacheable {

    @ApiModelProperty("修改前SKU")
    private GoodsSkuAuditVO thisSkuVO;

    @ApiModelProperty("修改后SKU")
    private GoodsSkuAuditVO draftSkuVO;
}
