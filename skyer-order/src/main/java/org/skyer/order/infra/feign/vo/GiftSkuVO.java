package org.skyer.order.infra.feign.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhanglongbing
 */
@Data
public class GiftSkuVO {

    @ApiModelProperty("设置唯一值id用于匹配")
    private String orderItemCode;
    /**
     * skuCode
     */
    private String skuCode;
    /**
     * 购买数量
     */
    private Integer num;
    /**
     * 随品skuCodeList
     */
    private List<PromoSkuVO> giftSkuCodeList;
    /**
     * 活动赠品skuCodeList
     */
    private List<PromoSkuVO> activitySkuCodeList;
}
