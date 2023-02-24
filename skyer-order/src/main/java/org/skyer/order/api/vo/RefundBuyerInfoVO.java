package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lzh
 * @date: 2022-03-16
 * @description:
 */
@Data
public class RefundBuyerInfoVO {

	@ApiModelProperty(value = "买家昵称")
	private String buyerNick;

	@ApiModelProperty(value = "退货人姓名")
	private String receiverName;

	@ApiModelProperty(value = "收货人手机号码")
	private String mobile;

	@ApiModelProperty(value = "地址")
	private String address;
}
