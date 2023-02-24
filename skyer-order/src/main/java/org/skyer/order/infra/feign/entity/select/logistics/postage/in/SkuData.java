package org.skyer.order.infra.feign.entity.select.logistics.postage.in;

import lombok.Builder;
import lombok.Data;

/**
 * @author chenzz
 */
@Data
@Builder
public class SkuData {
    private Integer number;

    private String sku;

}
