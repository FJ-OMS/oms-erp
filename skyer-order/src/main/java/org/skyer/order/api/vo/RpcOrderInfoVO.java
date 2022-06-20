package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xurongfa 2022-04-22 10:58
 */
@Data
public class RpcOrderInfoVO {
    @ApiModelProperty("内部订单编码")
    private String orderCode;

    @ApiModelProperty(value = "收获地址-省")
    private String provinceCode;

    @ApiModelProperty(value = "收获地址-市")
    private String cityCode;

    @ApiModelProperty(value = "收获地址-区")
    private String areaCode;

    @ApiModelProperty(value = "价格列表")
    private List<RpcOrderAmountVO> amountList;
}
