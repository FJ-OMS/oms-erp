package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

import org.skyer.boot.platform.lov.annotation.LovValue;
import org.skyer.common.SkyerCacheKey;
import org.skyer.core.cache.CacheValue;
import org.skyer.core.cache.Cacheable;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-03-16
 * @description:
 */
@Data
public class RefundBaseInfoVO implements Cacheable {
	@Encrypt
	private Long id;
	@ApiModelProperty(value = "店铺id")
	private Long storeId;
	@ApiModelProperty(value = "店铺名称")
	private String storeName;
	@ApiModelProperty(value = "退款类型/退货退款类型：1-未发货仅退款  1-退货退款")
	private String refundType;
	@ApiModelProperty(value = "退款/退货原因")
	private String reason;
	@ApiModelProperty(value = "退款/退货阶段   0-售中  1-售后")
	private String stage;
	@ApiModelProperty(value = "退款单类型： 普通退款单 ")
	private String orderRefundType;
	@ApiModelProperty(value = "买家备注")
	private String buyerRemarks;
	@ApiModelProperty(value = "客服备注")
	private String serviceRemarks;
	@ApiModelProperty(value = "结算方式")
	private String payType;
	@ApiModelProperty("标签code 多个,分割")
	private String tags;
	@ApiModelProperty(value = "审核状态")
	private String endorseStatus;

	@ApiModelProperty(value = "内部订单号")
	private String innerOrderNo;
	@ApiModelProperty(value = "平台订单号")
	private String outerOrderNo;
	@ApiModelProperty(value = "第三方退款/退货单号")
	private String outerRefundNo;
	@ApiModelProperty(value = "内部退款/退货单号")
	private String innerRefundNo;

	@ApiModelProperty(value = "完成时间")
	private Date completeDate;
	@ApiModelProperty(value = "内部创建时间")
	private Date creationDate;
	@ApiModelProperty(value = "三方退款/退回退款时间")
	private Date outReturnTime;


	@ApiModelProperty(value = "附件(可多个逗号隔开）")
	private String accessory;


	@ApiModelProperty(value = "审核完成时间")
	private Date auditCompleteTime;
	@ApiModelProperty(value = "用户退货完成时间")
	private Date buyerReturnTime;
	@ApiModelProperty(value = "仓库验收时间")
	private Date warehouseReceivingTime;
	@ApiModelProperty(value = "退款完成时间")
	private Date refundCompleteTime;


	@ApiModelProperty("渠道类型编码")
	@LovValue(lovCode = "SCHL.CHANNEL_TYPE", meaningField = "channelTypeMeaning")
	private String channel;
	@ApiModelProperty("渠道类型")
	private String channelTypeMeaning;

	@ApiModelProperty(value = "普通转单、手工单、复制单、导入单")
	private String actionType;

	@ApiModelProperty(value = "状态")
	private String status;

	private Long auditOperatorId;

	@ApiModelProperty(value = "审核拒绝原因")
	private String endorseRefuseReason;
	@ApiModelProperty("审核操作人")
	@CacheValue(
			key = SkyerCacheKey.USER,
			primaryKey = "auditOperatorId",
			searchKey = "realName",
			structure = CacheValue.DataStructure.MAP_OBJECT
	)
	private String auditOperator;
	@ApiModelProperty(value = "取消原因")
	private String cancelReason;

	@ApiModelProperty(value = "转单状态  0-  正常  1- 异常")
	private String exceptionStatus;
	@ApiModelProperty(value = "异常原因")
	private String exceptionReason;
}
