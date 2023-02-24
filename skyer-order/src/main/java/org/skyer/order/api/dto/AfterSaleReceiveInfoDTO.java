package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lzh
 * @date: 2022-04-26
 * @description:
 */
@Data
public class AfterSaleReceiveInfoDTO {

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

}
