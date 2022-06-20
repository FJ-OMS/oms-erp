package org.skyer.order.infra.feign.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PromoSkuVO {

    private Long activityId;

    private String skuCode;


    private String salePrice;

    private Long num;
}
