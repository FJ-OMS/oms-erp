package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author: lzh
 * @date: 2022-04-20
 * @description:
 */
@Data
public class ThirdPartyReceiveInfoDTO {

	@ApiModelProperty(value = "三方订单号")
	private String outerOrderNo;
	@ApiModelProperty(value = "三方退货单号")
	private String outerRefundNo;
	@ApiModelProperty(value = "退货人姓名")
	private String returnName;
	@ApiModelProperty(value = "退货人手机号码")
	private String mobile;
	@ApiModelProperty(value = "退货人省编码")
	private String provinceCode;
	@ApiModelProperty(value = "退货人所在省份")
	private String province;
	@ApiModelProperty(value = "退货人市编码")
	private String cityCode;
	@ApiModelProperty(value = "退货人所在市")
	private String city;
	@ApiModelProperty(value = "退货人区编码")
	private String districtCode;
	@ApiModelProperty(value = "退货人所在区")
	private String district;
	@ApiModelProperty(value = "退货人街道地址")
	private String town;
	@ApiModelProperty(value = "退货人详细地址")
	private String address;
	@ApiModelProperty(value = "退货人收件人姓名")
	private String receiveName;
	@ApiModelProperty(value = "退货人收件人手机号码")
	private String receiveMobile;
	@ApiModelProperty(value = "退货人收件人省编码")
	private String receiveProvinceCode;
	@ApiModelProperty(value = "退货人收件人所在省份")
	private String receiveProvince;
	@ApiModelProperty(value = "退货人收件人市编码")
	private String receiveCityCode;
	@ApiModelProperty(value = "退货人收件人所在市")
	private String receiveCity;
	@ApiModelProperty(value = "退货人收件人区编码")
	private String receiveDistrictCode;
	@ApiModelProperty(value = "退货人收件人所在区")
	private String receiveDistrict;
	@ApiModelProperty(value = "退货人收件人街道地址")
	private String receiveTown;
	@ApiModelProperty(value = "退货人收件人详细地址")
	private String receiveAddress;
}
