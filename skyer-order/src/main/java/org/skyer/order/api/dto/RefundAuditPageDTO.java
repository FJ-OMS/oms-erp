package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-03-22
 * @description:
 */
@Data
public class RefundAuditPageDTO extends PageReqDTO{

	@ApiModelProperty(value = "类型  1-仅退款单  2- 退货退款")
	private String type;

	@ApiModelProperty(value = "审核状态")
	private List<String> endorseStatusList;
	@ApiModelProperty(value = "退货单来源：普通转单、手工单、复制单、导入单")
	private String actionType;

	@ApiModelProperty(value = "第三方/内部订单号")
	private String orderNo;
	@ApiModelProperty(value = "退货物流单号")
	private String logisticsNo;
	@ApiModelProperty(value = "退货物流公司id")
	@Encrypt
	private Long logisticsCompanyId;
	@ApiModelProperty(value = "买家昵称")
	private String buyerNick;

	@ApiModelProperty(value = "退货人手机")
	private String mobile;
	@ApiModelProperty(value = "退货人")
	private String receiver;
	@ApiModelProperty(value = "三方退货/退款时间")
	private Date startTime;
	@ApiModelProperty(value = "三方退货/退款时间")
	private Date endTime;
	@ApiModelProperty(value = "结算方式")
	private String payType;

	@ApiModelProperty(value = "退款单类型")
	private String orderRefundType;

	@ApiModelProperty(value = "退款状态")
	private String status;

	@ApiModelProperty(value = "渠道")
	private String channelTypeCode;
	@ApiModelProperty(value = "店铺id")
	@Encrypt
	private List<Long> storeIdList;

}
