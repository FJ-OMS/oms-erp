package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-04-21
 * @description:
 */
@Data
public class ThirdPartyAfterSalePageDTO extends PageReqDTO{

	@ApiModelProperty(value = "类型  1-仅退款单  2- 退货退款")
	private String type;
	@ApiModelProperty(value = "第三方/内部订单号")
	private String orderNo;
	@ApiModelProperty(value = "买家昵称")
	private String buyerNick;
	@ApiModelProperty(value = "三方退货/退款时间")
	private Date startTime;
	@ApiModelProperty(value = "三方退货/退款时间")
	private Date endTime;
	@ApiModelProperty(value = "渠道")
	private String channelTypeCode;
	@ApiModelProperty(value = "店铺id")
	@Encrypt
	private List<Long> storeIdList;
	@ApiModelProperty(value = "转单状态  0- 正常  1-异常  tab页切换")
	private String exceptionStatus;
}
