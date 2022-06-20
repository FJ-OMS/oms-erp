package org.skyer.order.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import org.skyer.mybatis.domain.AuditDomain;
import java.math.BigDecimal;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 内部订单优惠券
 *
 * @author wushaochuan 2022-01-18 14:34:53
 */
@ApiModel("内部订单优惠券")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_coupon")
public class Coupon extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
    public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
    public static final String FIELD_CHANNEL = "channel";
    public static final String FIELD_OUTER_COUPON_ID = "outerCouponId";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_NUM = "num";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_DIVIDE_FLAG = "divideFlag";
    public static final String FIELD_PLATFORM_DIVIDE_AMT = "platformDivideAmt";
    public static final String FIELD_VENDER_DIVIDE_AMT = "venderDivideAmt";
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
    @ApiModelProperty(value = "平台订单号",required = true)
    @NotBlank
    private String outerOrderNo;
    @ApiModelProperty(value = "内部订单号",required = true)
    @NotBlank
    private String innerOrderNo;
    @ApiModelProperty(value = "订单来源渠道平台，如：淘宝、1688、京东、拼多多、抖音、苏宁、小米有品等",required = true)
    @NotBlank
    private String channel;
    @ApiModelProperty(value = "平台优惠券id",required = true)
    @NotNull
    private Long outerCouponId;
    @ApiModelProperty(value = "优惠券名称",required = true)
    @NotBlank
    private String name;
    @ApiModelProperty(value = "优惠券类型，如京东有：京券，平台京券，限品类京券，东券，平台东券，限品类东券，店铺京券，店铺限商品京券，店铺东券，店铺限商品东券，按比例分摊平台券，自营承担平台券，商家承担平台券",required = true)
    @NotBlank
    private String type;
    @ApiModelProperty(value = "优惠券数量",required = true)
    @NotNull
    private Long num;
    @ApiModelProperty(value = "优惠券面额，计价单位：元，保留小数点后2位",required = true)
    @NotNull
    private BigDecimal amount;
    @ApiModelProperty(value = "价格分摊标识：1-有分摊金额数据，0-无分摊金额数据]",required = true)
    @NotNull
    private Integer divideFlag;
    @ApiModelProperty(value = "商家分摊金额，计价单位：元，保留小数点后2位",required = true)
    @NotNull
    private BigDecimal platformDivideAmt;
    @ApiModelProperty(value = "平台分摊金额，计价单位：元，保留小数点后2位",required = true)
    @NotNull
    private BigDecimal venderDivideAmt;
    @ApiModelProperty(value = "系统字段：删除标识",required = true)
    @NotNull
    private Integer deleteFlag;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return 主键id
     */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    /**
     * @return 平台订单号
     */
	public String getOuterOrderNo() {
		return outerOrderNo;
	}

	public void setOuterOrderNo(String outerOrderNo) {
		this.outerOrderNo = outerOrderNo;
	}
    /**
     * @return 内部订单号
     */
	public String getInnerOrderNo() {
		return innerOrderNo;
	}

	public void setInnerOrderNo(String innerOrderNo) {
		this.innerOrderNo = innerOrderNo;
	}
    /**
     * @return 订单来源渠道平台，如：淘宝、1688、京东、拼多多、抖音、苏宁、小米有品等
     */
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
    /**
     * @return 平台优惠券id
     */
	public Long getOuterCouponId() {
		return outerCouponId;
	}

	public void setOuterCouponId(Long outerCouponId) {
		this.outerCouponId = outerCouponId;
	}
    /**
     * @return 优惠券名称
     */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    /**
     * @return 优惠券类型，如京东有：京券，平台京券，限品类京券，东券，平台东券，限品类东券，店铺京券，店铺限商品京券，店铺东券，店铺限商品东券，按比例分摊平台券，自营承担平台券，商家承担平台券
     */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    /**
     * @return 优惠券数量
     */
	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
    /**
     * @return 优惠券面额，计价单位：元，保留小数点后2位
     */
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
    /**
     * @return 价格分摊标识：1-有分摊金额数据，0-无分摊金额数据]
     */
	public Integer getDivideFlag() {
		return divideFlag;
	}

	public void setDivideFlag(Integer divideFlag) {
		this.divideFlag = divideFlag;
	}
    /**
     * @return 商家分摊金额，计价单位：元，保留小数点后2位
     */
	public BigDecimal getPlatformDivideAmt() {
		return platformDivideAmt;
	}

	public void setPlatformDivideAmt(BigDecimal platformDivideAmt) {
		this.platformDivideAmt = platformDivideAmt;
	}
    /**
     * @return 平台分摊金额，计价单位：元，保留小数点后2位
     */
	public BigDecimal getVenderDivideAmt() {
		return venderDivideAmt;
	}

	public void setVenderDivideAmt(BigDecimal venderDivideAmt) {
		this.venderDivideAmt = venderDivideAmt;
	}
    /**
     * @return 系统字段：删除标识
     */
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
