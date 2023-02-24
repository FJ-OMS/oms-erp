package org.skyer.order.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import lombok.Data;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 内部订单商品明细
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@ApiModel("内部订单商品明细")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_item")
@Data
public class Item extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
    public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
    public static final String FIELD_OUTER_ITEM_ID = "outerItemId";
    public static final String FIELD_SPU_CODE = "spuCode";
    public static final String FIELD_SPU_TYPE = "spuType";
    public static final String FIELD_SKU_CODE = "skuCode";
    public static final String FIELD_SKU_NAME = "skuName";
    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_REFUND_QTY = "refundQty";
    public static final String FIELD_RETURN_QTY = "returnQty";
    public static final String FIELD_SET_FLAG = "setFlag";
    public static final String FIELD_GIFT_FLAG = "giftFlag";
    public static final String FIELD_MULTI_FLAG = "multiFlag";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";

    public static final String FIELD_DELIVERY_STATUS = "deliveryStatus";
    public static final String FIELD_PREEMPTION_STATUS = "preemptionStatus";
    public static final String FIELD_SOURCE_STATUS = "sourceStatus";
    public static final String FIELD_SUSPEND_STATUS = "suspendStatus";

    public static final String FIELD_SYNC_PREEMP_STATUS = "syncPreempStatus";
    public static final String FIELD_SYNC_DELIVERY_STATUS = "syncDeliveryStatus";

    public static final String FIELD_REFUND_FLAG = "refundFlag";
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
    @ApiModelProperty(value = "商品类型1.实物2虚拟")
    private Integer spuType;
    @ApiModelProperty(value = "SKU编码")
    private String skuCode;
    @ApiModelProperty(value = "spu名称")
    private String spuName;
    @ApiModelProperty(value = "SKU名称")
    private String skuName;
    @ApiModelProperty(value = "数量")
    private Long quantity;
    @ApiModelProperty(value = "退款数量")
    private Long refundQty;
    @ApiModelProperty(value = "退货数量")
    private Long returnQty;
    @ApiModelProperty(value = "套餐标识：0-单品，1-套餐")
    private Integer setFlag;
    @ApiModelProperty(value = "赠品标识：0-卖品，1-赠品")
    private Integer giftFlag;
    @ApiModelProperty(value = "子母件标识：0-普通单品，1-子母件")
    private Integer multiFlag;
    @ApiModelProperty(value = "异常标识：0-正常，1-异常")
    private Integer exceptionFlag;
    @ApiModelProperty(value = "系统字段：删除标识")
    private Integer deleteFlag;

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

    @ApiModelProperty("外部销售总价")
    @Transient
    private BigDecimal outerSaleAmount;

    @ApiModelProperty("外部销售单价")
    @Transient
    private BigDecimal outerSingleSaleAmount;

    @ApiModelProperty("内部销售总价")
    @Transient
    private BigDecimal innerSaleAmount;

    @ApiModelProperty("内部销售单价")
    @Transient
    private BigDecimal innerSingleSaleAmount;

    @ApiModelProperty("外部均摊价格")
    @JsonIgnore
    @Transient
    private BigDecimal outBalancePrice;

    @ApiModelProperty("内部均摊价格")
    @JsonIgnore
    @Transient
    private BigDecimal innerBalancePrice;
    @ApiModelProperty(value = "成本价，计价单位：元，保留小数点后2位")
    @Transient
    private BigDecimal costPrice;
    @ApiModelProperty("成本总价")
    @Transient
    private BigDecimal costTotalAmount;

    @ApiModelProperty("运费")
    @Transient
    private BigDecimal transPrice;

    @ApiModelProperty(value = "退款标识：0-正常，1-退款")
    private Integer refundFlag;

    @ApiModelProperty(value = "挂起状态")
    private String suspendStatus;

    @ApiModelProperty(value = "店铺id")
    private Long storeId;

    @ApiModelProperty("明细code")
    private String orderItemCode;
    @ApiModelProperty(value = "赠品的主商品Code")
    private String mainItemCode;


    @ApiModelProperty(value = "标签")
    private String tags;

    @ApiModelProperty(value = "是否系统匹配营销赠品")
    private Boolean systemPickFlag;

    @ApiModelProperty(value = "表明哪次活动赠送")
    private Long activityId;

    @ApiModelProperty(value = "收货状态")
    private String receivingStatus;
}
