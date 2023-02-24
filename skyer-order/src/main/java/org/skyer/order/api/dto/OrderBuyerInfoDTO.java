package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 创建订单买家信息
 * @description
 * @Author wusc
 * @create 2022/1/18 1:48 下午
 */
@Data
public class OrderBuyerInfoDTO {

    @ApiModelProperty("买家昵称")
    @NotBlank(message = "买家昵称")
    private String buyerNick;

    @ApiModelProperty("收货人")
    @NotBlank(message = "收货人")
    private String receiverName;

    @ApiModelProperty("收货电话")
    private String mobile;

    @ApiModelProperty("收货手机号")
    @NotBlank(message = "收货人手机号")
    private String phone;

    @ApiModelProperty("收货省编码")
    @NotBlank(message = "收货省编码")
    private String provinceCode;

    @ApiModelProperty("收货省")
    @NotBlank(message = "收货省")
    private String province;

    @ApiModelProperty("收货市编码")
    @NotBlank(message = "收货市编码")
    private String cityCode;

    @ApiModelProperty("收货市")
    @NotBlank(message = "收货市")
    private String city;

    @ApiModelProperty("收货区编码")
    @NotBlank(message = "收货区编码不能为空")
    private String districtCode;

    @ApiModelProperty("收货区名称")
    @NotBlank(message = "收货区名称不能为空")
    private String district;

    @ApiModelProperty("街道")
    private String town;

    @ApiModelProperty("详细地址")
    @NotBlank(message = "详细地址不能为空")
    private String address;

    @ApiModelProperty("买家备注")
    private String buyerRemarks;

}
