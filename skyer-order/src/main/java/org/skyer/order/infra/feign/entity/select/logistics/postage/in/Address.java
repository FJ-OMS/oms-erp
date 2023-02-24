package org.skyer.order.infra.feign.entity.select.logistics.postage.in;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("地址对象")
@Data
public class Address {

    /**
     * 经度
     */
    private double lng;

    /**
     * 纬度
     */
    private double lat;

    private String provinceCode;

}
