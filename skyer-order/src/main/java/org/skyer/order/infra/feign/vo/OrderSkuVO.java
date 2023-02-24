package org.skyer.order.infra.feign.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderSkuVO {

    private String innerCode;


    private List<GiftSkuVO> giftSkuVOS;
    /**
     * 随品skuCodeList
     */
    private List<String> giftSkuCodeList;
}
