package org.skyer.order.infra.feign.dto;

import lombok.Data;

/**
 * @author zhanglongbing
 */
@Data
public class SearchGiftDTO {

    private Long activityId;

    private String orderItemCode;
    /**
     * sku编码
     */
    private String skuCode;

    /**
     * 购买数量
     */
    private Integer num;


}
