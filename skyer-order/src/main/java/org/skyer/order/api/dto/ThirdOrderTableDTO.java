package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zlb
 */
@Data
public class ThirdOrderTableDTO {
    @ApiModelProperty("基础信息")
    private ThirdOrderBaseInfoDTO thirdOrderBaseInfoDTO;

    @ApiModelProperty("买家信息")
    private ThirdOrderBuyerInfoDTO thirdOrderBuyerInfoDTO;

    @ApiModelProperty("支付信息")
    private ThirdOrderPaymentInfoDTO thirdOrderPaymentInfoDTO;

    @ApiModelProperty("优惠信息")
    private ThirdOrderDiscountInfoDTO thirdOrderDiscountInfoDTO;

    @ApiModelProperty("商品信息")
    private List<ThirdOrderItemDTO> thirdOrderItemDtoList;
}
