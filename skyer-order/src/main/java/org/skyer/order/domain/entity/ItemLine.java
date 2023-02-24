package org.skyer.order.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 内部订单商品行
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@ApiModel("内部订单商品行")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_item_line")
@Data
public class ItemLine extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
    public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
    public static final String FIELD_OUTER_ITEM_ID = "outerItemId";
    public static final String FIELD_INNER_ITEM_ID ="innerItemId";
    public static final String FIELD_SPU_CODE = "spuCode";
    public static final String FIELD_SPU_NAME = "spuName";
    public static final String FIELD_SPU_TYPE = "spuType";
    public static final String FIELD_SKU_CODE = "skuCode";
    public static final String FIELD_SKU_NAME = "skuName";
    public static final String FIELD_SUSPEND_STATUS = "suspendStatus";
    public static final String FIELD_ENDORSE_STATUS = "endorseStatus";
    public static final String FIELD_PREEMPTION_STATUS = "preemptionStatus";
    public static final String FIELD_SOURCE_STATUS = "sourceStatus";
    public static final String FIELD_DELIVERY_STATUS = "deliveryStatus";
    public static final String FIELD_PREEMP_NOTICE_ID = "preempNoticeId";
    public static final String FIELD_PREEMP_RESULT_ID = "preempResultId";
    public static final String FIELD_SOURCE_NOTICE_ID = "sourceNoticeId";
    public static final String FIELD_SOURCE_RESULT_ID = "sourceResultId";
    public static final String FIELD_CHANNEL_WAREHOUSE_ID = "channelWarehouseId";
    public static final String FIELD_VIRTUAL_WAREHOUSE_ID = "virtualWarehouseId";
    public static final String FIELD_LOGICAL_WAREHOUSE_ID = "logicalWarehouseId";
    public static final String FIELD_REFUND_FLAG = "refundFlag";
    public static final String FIELD_RETURN_FLAG = "returnFlag";
    public static final String FIELD_GIFT_FLAG = "giftFlag";
    public static final String FIELD_SET_FLAG = "setFlag";
    public static final String FIELD_MULTI_FLAG = "multiFlag";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_SYNC_PREEMP_STATUS = "syncPreempStatus";
    public static final String FIELD_SYNC_DELIVERY_STATUS = "syncDeliveryStatus";
    public static final String FIELD_AUDIT_ID = "auditId";
    public static final String FIELD_PACKAGE_NO = "packageNo";
    public static final String FIELD_STORE_ID = "storeId";
    public static final String FIELD_RECEIVING = "receivingStatus";

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
    @ApiModelProperty("itemLineCode")
    private String itemLineCode;
    @ApiModelProperty(value = "skuId")
    private Long skuId;
   @ApiModelProperty(value = "平台订单号")    
    private String outerOrderNo;
   @ApiModelProperty(value = "内部订单号")    
    private String innerOrderNo;
   @ApiModelProperty(value = "平台商品明细ID")    
    private Long outerItemId;
   @ApiModelProperty(value = "商品编码")    
    private String spuCode;
   @ApiModelProperty(value = "spu名称")
   private String spuName;
   @ApiModelProperty(value = "商品类型1.实物2虚拟")    
    private Integer spuType;
   @ApiModelProperty(value = "SKU编码")    
    private String skuCode;
   @ApiModelProperty(value = "SKU名称")    
    private String skuName;
   @ApiModelProperty(value = "挂起状态")    
    private String suspendStatus;
   @ApiModelProperty(value = "审核状态")    
    private String endorseStatus;
   @ApiModelProperty(value = "预占状态")    
    private String preemptionStatus;
   @ApiModelProperty(value = "寻源状态")    
    private String sourceStatus;
   @ApiModelProperty(value = "发货状态")    
    private String deliveryStatus;
    @ApiModelProperty(value = "当前状态")
    private String currentStatus;

    @ApiModelProperty(value = "预占后，渠道仓数量同步平台状态")
    private String syncPreempStatus;
    @ApiModelProperty(value = "发（收）货同步平台状态")
    private String syncDeliveryStatus;
   @ApiModelProperty(value = "预占通知单id")    
    private Long preempNoticeId;
   @ApiModelProperty(value = "预占结果单id")    
    private Long preempResultId;
   @ApiModelProperty(value = "寻源通知单id")    
    private Long sourceNoticeId;
   @ApiModelProperty(value = "寻源结果单id")    
    private Long sourceResultId;
   @ApiModelProperty(value = "预占渠道仓id")    
    private Long channelWarehouseId;
   @ApiModelProperty(value = "预占虚仓id")    
    private Long virtualWarehouseId;
   @ApiModelProperty(value = "寻源结果逻辑仓id")    
    private Long logicalWarehouseId;
   @ApiModelProperty(value = "退款标识：0-正常，1-退款")    
    private Integer refundFlag;
   @ApiModelProperty(value = "退货标识：0-正常，1-退货")    
    private Integer returnFlag;
   @ApiModelProperty(value = "赠品标识：0-卖品，1-赠品")    
    private Integer giftFlag;
   @ApiModelProperty(value = "套餐标识：0-单品，1-套餐")    
    private Integer setFlag;
   @ApiModelProperty(value = "子母件标识：0-普通单品，1-子母件")    
    private Integer multiFlag;
   @ApiModelProperty(value = "审核单id")
   private Long auditId;
   @ApiModelProperty(value = "系统字段：删除标识")
    private Integer deleteFlag;

   @ApiModelProperty(value = "发（收）货通知单号")
	private String deliveryNoticeNo;

   @ApiModelProperty(value = "发（收）货结果单号")
   private String deliveryResultNo;

   @ApiModelProperty(value = "内部itemid")
   private Long innerItemId;

    @ApiModelProperty("外部销售价")
    @Transient
    private BigDecimal outerSaleAmount;

    @ApiModelProperty("内部销售价")
    @Transient
    private BigDecimal innerSaleAmount;

    @ApiModelProperty("平台平摊价")
    @Transient
    private BigDecimal outBalanceAmount;

    @ApiModelProperty("内部平摊价")
    @Transient
    private BigDecimal innerBalanceAmount;

    @ApiModelProperty("运费")
    @Transient
    private BigDecimal transPrice;

    @ApiModelProperty(value = "成本价，计价单位：元，保留小数点后2位")
    @Transient
    private BigDecimal costPrice;

    @ApiModelProperty(value = "预占虚仓名称")
    private String virtualWarehouseName;

    @ApiModelProperty("挂起类型")
    private Integer suspendType;

    @ApiModelProperty(value = "逻辑仓编码")
    private String logicalWarehouseCode;

    @ApiModelProperty(value = "逻辑仓名称")
    private String logicalWarehouseName;

    @ApiModelProperty(value = "包裹号")
    private String packageNo;

    @ApiModelProperty(value = "快递单号")
    private String expressNo;

    @ApiModelProperty(value = "物流商编码")
    private String logisticsCompanyCode;

    @ApiModelProperty(value = "物流商名称")
    private String logisticsCompany;

    @ApiModelProperty(value = "物流业务")
    private String logisticsBusiness;

    private String wmsCode;

    @ApiModelProperty(value = "店铺id")
    private Long storeId;

    @ApiModelProperty(value = "取消状态")
    private String cancelStatus;
    @ApiModelProperty(value = "标签")
    private String tags;

    @ApiModelProperty(value = "收货状态")
    private String receivingStatus;
}
