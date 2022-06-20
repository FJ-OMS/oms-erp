package org.skyer.order.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-03-18
 * @description:
 */
@Data
public class RefundPageDTO extends PageReqDTO {


	@ApiModelProperty(value = "第三方/内部订单号")
	private String orderNo;
	@ApiModelProperty(value = "退货物流单号")
	private String logisticsNo;
	@ApiModelProperty(value = "退货单来源：普通转单、手工单、复制单、导入单")
	private String actionType;
	@ApiModelProperty(value = "买家昵称")
	private String buyerNick;
	@ApiModelProperty(value = "渠道")
	private String channelTypeCode;
	@ApiModelProperty(value = "店铺id")
	@Encrypt
	private List<Long> storeIdList;
	@ApiModelProperty(value = "退货人")
	private String receiver;
	@ApiModelProperty(value = "状态")
	private String status;
	@ApiModelProperty(value = "三方退货时间")
	private Date startTime;
	@ApiModelProperty(value = "三方退货时间")
	private Date endTime;
	@ApiModelProperty(value = "类型")
	@JsonIgnore
	private String type;
	@ApiModelProperty(value = "内部退款/退货单号",hidden = true)
	private String innerRefundNo;
}
