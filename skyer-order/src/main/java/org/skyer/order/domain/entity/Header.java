package org.skyer.order.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import lombok.Data;

import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.starter.keyencrypt.core.Encrypt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 内部订单头
 *
 * @author wushaochuan 2022-01-18 14:34:53
 */
@ApiModel("内部订单头")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_header")
@Data
public class Header extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
    public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
    public static final String FIELD_TRADE_TYPE = "tradeType";
    public static final String FIELD_CHANNEL = "channel";
    public static final String FIELD_ACTION_TYPE = "actionType";
    public static final String FIELD_BUSINESS_TYPE = "businessType";
    public static final String FIELD_PAY_TYPE = "payType";
    public static final String FIELD_STORE_ID = "storeId";
    public static final String FIELD_STORE_CODE = "storeCode";
    public static final String FIELD_STORE_NAME = "storeName";
    public static final String FIELD_PAY_STATUS = "payStatus";
    public static final String FIELD_CANCEL_STATUS = "cancelStatus";
    public static final String FIELD_ORDER_STATUS = "orderStatus";
    public static final String FIELD_REFUND_STATUS = "refundStatus";
    public static final String FIELD_RETURN_STATUS = "returnStatus";
    public static final String FIELD_ORDER_REMARKS = "orderRemarks";
    public static final String FIELD_BUYER_REMARKS = "buyerRemarks";
    public static final String FIELD_SERVICE_REMARKS = "serviceRemarks";
    public static final String FIELD_ENDORSE_OPINION = "endorseOpinion";
    public static final String FIELD_CANCEL_REASON = "cancelReason";
    public static final String FIELD_EXCEPTION_REASON = "exceptionReason";
    public static final String FIELD_BUYER_FLAG = "buyerFlag";
    public static final String FIELD_BUYER_NICK = "buyerNick";
    public static final String FIELD_SKU_QUATITY = "skuQuatity";
    public static final String FIELD_GOODS_QUATITY = "goodsQuatity";
    public static final String FIELD_ORDER_TIME = "orderTime";
    public static final String FIELD_PAY_TIME = "payTime";
    public static final String FIELD_MODIFIED_TIME = "modifiedTime";
    public static final String FIELD_RECEIVED_TIME = "receivedTime";
    public static final String FIELD_END_TIME = "endTime";
    public static final String FIELD_PULL_TIME = "pullTime";
    public static final String FIELD_TRANSFER_TIME = "transferTime";
    public static final String FIELD_RESET_TIME = "resetTime";
    public static final String FIELD_BPMN_PROCESS_ID = "bpmnProcessId";
    public static final String FIELD_BPMN_INSTANCE_ID = "bpmnInstanceId";
    public static final String FIELD_BPMN_VERSION_NUM = "bpmnVersionNum";
    public static final String FIELD_EXCEPTION_FLAG = "exceptionFlag";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";

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
    @ApiModelProperty(value = "平台订单号")
    private String outerOrderNo;
    @ApiModelProperty(value = "内部订单号")
    private String innerOrderNo;
    @ApiModelProperty(value = "订单交易类型：销售单、仅退款单、退货退款单、换货单")
    private String tradeType;
    @ApiModelProperty(value = "订单来源平台，如：淘宝、1688、京东、拼多多、抖音、苏宁、小米有品等")
    private String channel;
    @ApiModelProperty(value = "订单创建类型：普通转单、手工单、复制单、导入单")
    private String actionType;
    @ApiModelProperty(value = "业务类型，如：手工销售订单、直营销售订单、菜鸟外仓销售订单、京东外仓销售订单、直营退货退款订单、平台O2O销售订单、平台预售定金单等")
    private String businessType;
    @ApiModelProperty(value = "支付方式")
    private String payType;
    @ApiModelProperty(value = "店铺id")
    private Long storeId;
    @ApiModelProperty(value = "店铺编码")
    private String storeCode;
    @ApiModelProperty(value = "店铺名称")
    private String storeName;
    @ApiModelProperty(value = "支付状态")
    private Integer payStatus;
    @ApiModelProperty(value = "取消状态")
    private Integer cancelStatus;
    @ApiModelProperty(value = "订单状态")
    private String orderStatus;
    @ApiModelProperty(value = "退款状态")
    private Integer refundStatus;
    @ApiModelProperty(value = "退货状态")
    private Integer returnStatus;
    @ApiModelProperty(value = "订单备注")
    private String orderRemarks;
    @ApiModelProperty(value = "买家备注/留言")
    private String buyerRemarks;
    @ApiModelProperty(value = "客服备注")
    private String serviceRemarks;
    @ApiModelProperty(value = "审核意见（订单级）")
    private String endorseOpinion;
    @ApiModelProperty(value = "取消原因（详情页，手动点击取消）")
    private String cancelReason;
    @ApiModelProperty(value = "异常单原因")
    private String exceptionReason;
    @ApiModelProperty(value = "买家旗帜")
    private String buyerFlag;
    @ApiModelProperty(value = "买家昵称")
    private String buyerNick;
    @ApiModelProperty(value = "商品SKU种数")
    private Long skuQuatity;
    @ApiModelProperty(value = "商品数量")
    private Long goodsQuatity;
    @ApiModelProperty(value = "下单时间")
    private Date orderTime;
    @ApiModelProperty(value = "支付时间")
    private Date payTime;
    @ApiModelProperty(value = "交易修改时间(用户对订单的任何修改都会更新此字段)。格式:yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;
    @ApiModelProperty(value = "确认收货时间")
    private Date receivedTime;
    @ApiModelProperty(value = "交易结束时间")
    private Date endTime;
    @ApiModelProperty(value = "拉单时间")
    private Date pullTime;
    @ApiModelProperty(value = "转单时间")
    private Date transferTime;
    @ApiModelProperty(value = "订单重置时间")
    private Date resetTime;
    @ApiModelProperty(value = "工作流部署ID")
    private String bpmnProcessId;
    @ApiModelProperty(value = "工作流实例 ID")
    private Long bpmnInstanceId;
    @ApiModelProperty(value = "工作流版本号")
    private Long bpmnVersionNum;
    @ApiModelProperty(value = "异常标识：0-正常，1-异常")
    private Integer exceptionFlag;
    @ApiModelProperty(value = "系统字段：删除标识")
    private Integer deleteFlag;

    @ApiModelProperty(value = "订单级-挂起状态：0-正常，1-挂起成功，2-挂起失败，3-解挂成功，4-解挂失败")
    private Integer suspendStatus;

    @ApiModelProperty(value = "聚合商品行的审核状态")
    private String endorseStatus;

    @ApiModelProperty(value = "聚合商品行的预占状态")
    private String preemptionStatus;

    @ApiModelProperty(value = "聚合商品行的寻源状态")
    private String sourceStatus;

    @ApiModelProperty(value = "聚合商品行的发（收）货状态")
    private String deliveryStatus;

    @ApiModelProperty(value = "聚合商品行，预占后渠道仓数量同步平台状态")
    private String syncPreempStatus;

    @ApiModelProperty(value = "聚合商品行，发（收）货同步平台状态")
    private String syncDeliveryStatus;

    @ApiModelProperty(value = "异常编码")
    private String exceptionCode;

    @ApiModelProperty("复制来源")
    private String copyOrigin;

    @ApiModelProperty("挂起类型")
    private Integer suspendType;

    @ApiModelProperty("挂起原因")
    private String suspendReason;

    @ApiModelProperty("标签code 多个,分割")
    private String tags;

    @ApiModelProperty(value = "预计发货时间")
    private Date predictDeliveryTime;

    @ApiModelProperty(value = "收货状态")
    private String receivingStatus;

    @ApiModelProperty(value = "支付价格")
    @Transient
    private BigDecimal payAmount;

    @ApiModelProperty(value = "bpmn的jobkey 用于结束预占流程")
    private Long bpmnJobKey;

    @ApiModelProperty(value = "bpmn创单流程是否结束")
    private Boolean bpmnJobComplete;

    @ApiModelProperty(value = "提货店铺Id")
    private Long takingDeliveryStoreId;

    @ApiModelProperty(value = "提货店铺名称")
    private String takingDeliveryStoreName;

    @ApiModelProperty(value = "销售员工")
    private String salesEmployeeRealName;

    @ApiModelProperty(value = "提货方式")
    private String takingDeliveryWay;

    @ApiModelProperty(value = "员工ID")
    private Long salesEmployeeId;

    @ApiModelProperty(value = "凭证")
    private String voucher;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "组织ID")
    @Encrypt
    private Long salesOrganizingId;
}
