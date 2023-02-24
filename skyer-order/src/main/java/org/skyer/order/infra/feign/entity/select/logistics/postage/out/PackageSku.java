package org.skyer.order.infra.feign.entity.select.logistics.postage.out;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PackageSku {

    /**
     * spu
     */
    private String spu;

    /**
     * sku
     */
    private String sku;

    /**
     * 商品重量
     */
    private BigDecimal weight;

    /**
     * 数量
     */
    private Integer number;
}
