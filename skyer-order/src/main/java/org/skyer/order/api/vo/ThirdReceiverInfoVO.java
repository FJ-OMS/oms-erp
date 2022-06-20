package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description
 * @Author wusc
 * @create 2022/2/10 4:17 下午
 */
@Data
public class ThirdReceiverInfoVO {
    @ApiModelProperty(value = "昵称")
    private String buyerNick;

    @ApiModelProperty(value = "收货人")
    private String receiverName;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String district;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @ApiModelProperty(value = "区编码")
    private String districtCode;
}
