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
import org.skyer.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 第三方订单头
 *
 * @author zhanglongbing@ebc.com 2022-04-20 10:15:56
 */
@ApiModel("第三方订单头")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_third_header")
@Data
public class ThirdHeader extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
    public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
    public static final String FIELD_CHANNEL = "channel";
    public static final String FIELD_BUSINESS_TYPE = "businessType";
    public static final String FIELD_STORE_ID = "storeId";
    public static final String FIELD_STORE_CODE = "storeCode";
    public static final String FIELD_STORE_NAME = "storeName";
    public static final String FIELD_CIRCULATION_STATUS = "circulationStatus";
    public static final String FIELD_FAILURE_REASON = "failureReason";
    public static final String FIELD_SYNC_STATUS = "syncStatus";
    public static final String FIELD_RECEIVING_STATUS = "receivingStatus";
    public static final String FIELD_BUYER_REMARKS = "buyerRemarks";
    public static final String FIELD_SERVICE_REMARKS = "serviceRemarks";
    public static final String FIELD_GOODS_QUANTITY = "goodsQuantity";
    public static final String FIELD_TOTAL_AMOUNT = "totalAmount";
    public static final String FIELD_TOTAL_DISCOUNT = "totalDiscount";
    public static final String FIELD_BUYER_PAY_AMOUNT = "buyerPayAmount";
    public static final String FIELD_AMOUNT_RECEIVABLE = "amountReceivable";
    public static final String FIELD_BUSINESS_DISCOUNT_AMOUNT = "businessDiscountAmount";
    public static final String FIELD_THIRD_DISCOUNT_AMOUNT = "thirdDiscountAmount";
    public static final String FIELD_BUYER_NICK = "buyerNick";
    public static final String FIELD_ORDER_TIME = "orderTime";
    public static final String FIELD_PAY_TIME = "payTime";

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
    @ApiModelProperty(value = "三方订单号")
    private String outerOrderNo;
    @ApiModelProperty(value = "渠道")
    private String channel;
    @ApiModelProperty(value = "业务类型 1 普通单")
    private String businessType;
    @ApiModelProperty(value = "店铺id")
    private Long storeId;
    @ApiModelProperty(value = "店铺编码")
    private String storeCode;
    @ApiModelProperty(value = "店铺名称")
    private String storeName;
    @ApiModelProperty(value = "流转状态 0流转失败 1流转成功")
    private String circulationStatus;
    @ApiModelProperty(value = "流转失败原因")
    private String failureReason;
    @ApiModelProperty(value = "回写状态 0：未回写 1：回写成功 2：回写失败")
    private String syncStatus;
    @ApiModelProperty(value = "平台收货状态 0：未收货 1：已收货")
    private String receivingStatus;
    @ApiModelProperty(value = "买家备注/留言")
    private String buyerRemarks;
    @ApiModelProperty(value = "客服备注")
    private String serviceRemarks;
    @ApiModelProperty(value = "商家/订单备注")
    private String orderRemarks;
    @ApiModelProperty(value = "商品（行）总数量")
    private Long goodsQuantity;
    @ApiModelProperty(value = "是否有优惠标识")
    private Integer discountFlag;
    @ApiModelProperty(value = "总金额")
    private BigDecimal totalAmount;
    @ApiModelProperty(value = "优惠总金额")
    private BigDecimal totalDiscount;
    @ApiModelProperty(value = "买家实付")
    private BigDecimal buyerPayAmount;
    @ApiModelProperty(value = "应收总金额")
    private BigDecimal amountReceivable;
    @ApiModelProperty(value = "商家优惠金额")
    private BigDecimal businessDiscountAmount;
    @ApiModelProperty(value = "三方承担优惠金额")
    private BigDecimal thirdDiscountAmount;
    @ApiModelProperty(value = "买家昵称")
    private String buyerNick;
    @ApiModelProperty(value = "下单时间")
    private Date orderTime;
    @ApiModelProperty(value = "支付时间")
    private Date payTime;
    @ApiModelProperty(value = "支付类型")
    private String payType;
    @ApiModelProperty(value = "支付状态")
    private String payStatus;
    @ApiModelProperty(value = "拉单Json")
    private String outJsonInfo;
    @ApiModelProperty(value = "拉单jsonMd5值")
    private String outJsonInfoMd5;

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
	//
    // 非数据库字段
    // ------------------------------------------------------------------------------



}
