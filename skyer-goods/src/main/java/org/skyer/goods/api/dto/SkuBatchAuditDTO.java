package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;


/**
 *
 * @author zhanglb
 */
@Data
public class SkuBatchAuditDTO {

    @ApiModelProperty("skuId列表")
    @Encrypt
    List<Long> skuIds;

}
