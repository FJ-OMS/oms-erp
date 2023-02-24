package org.skyer.order.infra.feign.entity.select.logistics.postage.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Position {

    /**
     * 经度
     */
    private double lng;

    /**
     * 纬度
     */
    private double lat;

}
