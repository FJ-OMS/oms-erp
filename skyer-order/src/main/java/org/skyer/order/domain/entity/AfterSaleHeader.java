package org.skyer.order.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

import org.skyer.mybatis.domain.AuditDomain;

import java.math.BigDecimal;
import java.util.Date;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.starter.keyencrypt.core.Encrypt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 售后单
 *
 * @author lzh 2022-03-16 10:55:39
 */
@ApiModel("售后单")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_after_sale_header")
@Data
public class AfterSaleHeader extends AuditDomain {

	public static final String FIELD_ID = "id";
	public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
	public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
	public static final String FIELD_OUTER_REFUND_NO = "outerRefundNo";
	public static final String FIELD_INNER_REFUND_NO = "innerRefundNo";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_ENDORSE_STATUS = "endorseStatus";
	public static final String FIELD_CHANNEL = "channel";
	public static final String FIELD_STORE_ID = "storeId";
	public static final String FIELD_STORE_NAME = "storeName";
	public static final String FIELD_STORE_CODE = "storeCode";
	public static final String FIELD_BUYER_NICK = "buyerNick";
	public static final String FIELD_ACTION_TYPE = "actionType";
	public static final String FIELD_REFUND_TYPE = "refundType";
	public static final String FIELD_OUT_RETURN_TIME = "outReturnTime";
	public static final String FIELD_REASON = "reason";
	public static final String FIELD_REMARK = "remark";
	public static final String FIELD_STATUS = "status";
	public static final String FIELD_PAY_TYPE = "payType";
	public static final String FIELD_RECEIVER_NAME = "receiverName";
	public static final String FIELD_GOODS_QUATITY = "goodsQuatity";
	public static final String FIELD_REFUND_AMOUNT = "refundAmount";
	public static final String FIELD_TAGS = "tags";
	public static final String FIELD_ACCESSORY = "accessory";
	public static final String FIELD_COMPLETE_DATE = "completeDate";
	public static final String FIELD_ENDORSE_REFUSE_REASON = "endorseRefuseReason";
	public static final String FIELD_DELETE_FLAG = "deleteFlag";
	public static final String FIELD_RECEIVING_WAREHOUSE = "receivingWarehouse";
	public static final String FIELD_RECEIVING_WAREHOUSE_NAME = "receivingWarehouseName";
	public static final String FIELD_RECEIVING_WAREHOUSE_STATUS = "receivingWarehouseStatus";
	public static final String FIELD_RETURN_LOGISTICS_COMPANY = "returnLogisticsCompany";
	public static final String FIELD_RETURN_LOGISTICS_NO = "returnLogisticsNo";

	//
	// 业务方法(按public protected private顺序排列)
	// ------------------------------------------------------------------------------

	//
	// 数据库字段
	// ------------------------------------------------------------------------------


	@ApiModelProperty("主键id")
	@Id
	@GeneratedValue
	private Long id;
	@ApiModelProperty(value = "内部订单号")
	private String innerOrderNo;
	@ApiModelProperty(value = "平台订单号")
	private String outerOrderNo;
	@ApiModelProperty(value = "第三方退款/退货单号")
	private String outerRefundNo;
	@ApiModelProperty(value = "内部退款/退货单号")
	private String innerRefundNo;
	@ApiModelProperty(value = "售后类型 1-退款单  2- 退货退款")
	private String type;
	@ApiModelProperty(value = "审核状态")
	private String endorseStatus;
	@ApiModelProperty(value = "渠道")
	private String channel;
	@ApiModelProperty(value = "店铺id")
	private Long storeId;
	@ApiModelProperty(value = "店铺名称")
	private String storeName;
	@ApiModelProperty(value = "店铺编码")
	private String storeCode;
	@ApiModelProperty(value = "买家昵称")
	private String buyerNick;
	@ApiModelProperty(value = "普通转单、手工单、复制单、导入单")
	private String actionType;
	@ApiModelProperty(value = "退款类型/退货退款类型：1-未发货仅退款  1-退货退款")
	private String refundType;
	@ApiModelProperty(value = "三方退款/退回退款时间")
	private Date outReturnTime;
	@ApiModelProperty(value = "退款/退货原因")
	private String reason;
	@ApiModelProperty(value = "退款/退货阶段   0-售中  1-售后")
	private String stage;
	@ApiModelProperty(value = "结算方式")
	private String payType;
	@ApiModelProperty(value = "收货人姓名")
	private String receiverName;
	@ApiModelProperty(value = "收货人手机号码")
	private String mobile;
	@ApiModelProperty(value = "收货人详细地址")
	private String address;

	@ApiModelProperty(value = "商品行总数")
	private Integer goodsQuantity;
	@ApiModelProperty(value = "退款总金额")
	private BigDecimal refundAmount;
	@ApiModelProperty(value = "标签code 多个,分割")
	private String tags;
	@ApiModelProperty(value = "附件(可多个逗号隔开）")
	private String accessory;
	@ApiModelProperty(value = "完成时间")
	private Date completeDate;
	@ApiModelProperty(value = "审核拒绝原因")
	private String endorseRefuseReason;
	@ApiModelProperty(value = "删除标识")
	private Integer deleteFlag;
	@ApiModelProperty(value = "收货仓库")
	private Long receivingWarehouseId;
	@ApiModelProperty(value = "收货仓库编码")
	private String receivingWarehouseCode;
	@ApiModelProperty(value = "收货仓库名称")
	private String receivingWarehouseName;
	@ApiModelProperty(value = "仓库收货状态")
	private String receivingWarehouseStatus;
	@ApiModelProperty(value = "状态")
	private String status;
	@ApiModelProperty(value = "退款单类型")
	private String orderRefundType;


	@ApiModelProperty(value = "审核完成时间")
	private Date auditCompleteTime;
	@ApiModelProperty(value = "用户退货完成时间")
	private Date buyerReturnTime;
	@ApiModelProperty(value = "仓库验收时间")
	private Date warehouseReceivingTime;
	@ApiModelProperty(value = "退款完成时间")
	private Date refundCompleteTime;


	@ApiModelProperty(value = "原订单ID")
	@Encrypt
	private Long originOrderId;

	@ApiModelProperty(value = "买家备注/留言")
	private String buyerRemarks;
	@ApiModelProperty(value = "客服备注")
	private String serviceRemarks;
	@ApiModelProperty(value = "工作流部署ID")
	private String bpmnProcessId;
	@ApiModelProperty(value = "工作流实例 ID")
	private Long bpmnInstanceId;
	@ApiModelProperty(value = "工作流jobkey")
	private String bpmnJobKey;
	@ApiModelProperty(value = "转单状态  0-  正常  1- 异常")
	private String exceptionStatus;
	@ApiModelProperty(value = "异常原因")
	private String exceptionReason;
	@ApiModelProperty(value = "通知单ID(订单中心)")
	private Long orderWarehousingNoticeId;


	@ApiModelProperty(value = "入库通知单id")
	private Long warehouseWarrantId;

	@ApiModelProperty(value = "入库通知单号")
	private String warehouseWarrantCode;

	@ApiModelProperty(value = "入库结果单id")
	private Long warehouseResultId;

	@ApiModelProperty(value = "入库结果单号")
	private String warehouseResultCode;

	@ApiModelProperty(value = "入库工作流key")
	private String warehouseJobKey;

	@ApiModelProperty(value = "审核人ID")
	private Long auditOperatorId;

	//
	// 非数据库字段
	// ------------------------------------------------------------------------------

	//
	// getter/setter
	// ------------------------------------------------------------------------------


}
