package org.skyer.order.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 内部订单商品明细
 *
 * @author zhanglongbing@ebc.com 2022-04-20 10:15:56
 */
@ApiModel("内部订单商品明细")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_third_item")
@Data
public class ThirdItem extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
    public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
    public static final String FIELD_OUTER_ITEM_ID = "outerItemId";
    public static final String FIELD_SPU_CODE = "spuCode";
    public static final String FIELD_SPU_TYPE = "spuType";
    public static final String FIELD_SKU_CODE = "skuCode";
    public static final String FIELD_SKU_NAME = "skuName";
    public static final String FIELD_QUANTITY = "quantity";
    public static final String FIELD_SET_FLAG = "setFlag";
    public static final String FIELD_GIFT_FLAG = "giftFlag";
    public static final String FIELD_MULTI_FLAG = "multiFlag";
    public static final String FIELD_EXCEPTION_FLAG = "exceptionFlag";
    public static final String FIELD_EXCEPTION_REASON = "exceptionReason";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_SPU_NAME = "spuName";
    public static final String FIELD_SKU_ID = "skuId";
    public static final String FIELD_MAIN_ITEM_CODE = "mainItemCode";
    public static final String FIELD_ORDER_ITEM_CODE = "orderItemCode";
    public static final String FIELD_SYSTEM_PICK_FLAG = "systemPickFlag";
    public static final String FIELD_ACTIVITY_ID = "activityId";

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
    @ApiModelProperty(value = "平台商品明细ID")
    private Long outerItemId;
    @ApiModelProperty(value = "商品编码")
    private String spuCode;
    @ApiModelProperty(value = "商品类型1.实物2虚拟")
    private Integer spuType;
    @ApiModelProperty(value = "SKU编码")
    private String skuCode;
    @ApiModelProperty(value = "SKU名称")
    private String skuName;
    @ApiModelProperty(value = "数量")
    private Long quantity;
    @ApiModelProperty(value = "套餐标识：0-单品，1-套餐")
    private Integer setFlag;
    @ApiModelProperty(value = "赠品标识：0-卖品，1-赠品")
    private Integer giftFlag;
    @ApiModelProperty(value = "子母件标识：0-普通单品，1-子母件")
    private Integer multiFlag;
    @ApiModelProperty(value = "异常标识：0-正常，1-异常")
    private Integer exceptionFlag;
    @ApiModelProperty(value = "异常单原因（转单因商品的原因异常）")
    private String exceptionReason;
    @ApiModelProperty(value = "系统字段：删除标识")
    private Integer deleteFlag;
    @ApiModelProperty(value = "spu名称")
    private String spuName;
    @ApiModelProperty(value = "skuId")
    private Long skuId;
    @ApiModelProperty(value = "该商品如果是赠品-标明是主商品")
    private String mainItemCode;
    @ApiModelProperty(value = "订单商品code")
    private String orderItemCode;
    @ApiModelProperty(value = "是否系统匹配营销赠品标识")
    private Integer systemPickFlag;
    @ApiModelProperty(value = "指明是哪次活动赠送")
    private Long activityId;

    @ApiModelProperty("外部销售总价")
    private BigDecimal outerSaleAmount;

    @ApiModelProperty("外部销售单价")
    private BigDecimal outerSingleSaleAmount;
	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------



}
