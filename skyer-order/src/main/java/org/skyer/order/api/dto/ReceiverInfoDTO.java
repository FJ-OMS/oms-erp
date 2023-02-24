package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 收货人信息
 * @description
 * @Author wusc
 * @create 2022/2/16 1:32 下午
 */
@Data
public class ReceiverInfoDTO {


    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "省code")
    private String provinceCode;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市code")
    private String cityCode;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区code")
    private String districtCode;

    @ApiModelProperty(value = "区")
    private String district;

    @ApiModelProperty(value = "街")
    private String town;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "收货人手机号码")
    private String mobile;
}
