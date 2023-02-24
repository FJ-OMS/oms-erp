package org.skyer.goods.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;



import org.skyer.goods.api.dto.AttributeDTO;
import org.skyer.mybatis.domain.AuditDomain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;

/**
 * 商品规格表
 *
 * @author zhanglongbing@ebc.com 2021-12-27 14:48:20
 */
@ApiModel("商品规格表")
@VersionAudit
@ModifyAudit
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_sku")
public class Sku extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_SPU_ID = "spuId";
    public static final String FIELD_SKU_CODE = "skuCode";
    public static final String FIELD_SHOW_FLAG = "showFlag";
    public static final String FIELD_ATTRIBUTE_VALUE = "attributeValue";
    public static final String FIELD_MD5_CHECK_VALUE = "md5CheckValue";
    public static final String FIELD_SKU_NAME = "skuName";
    public static final String FIELD_COST_PRICE = "costPrice";
    public static final String FIELD_SALES_PRICE = "salesPrice";
    public static final String FIELD_ACTIVITY_PRICE = "activityPrice";
    public static final String FIELD_MARKET_PRICE = "marketPrice";
    public static final String FIELD_DISTRIBUTION_PRICE = "distributionPrice";
    public static final String FIELD_WHOLESALE_PRICE = "wholesalePrice";
    public static final String FIELD_CLEARING_PRICE = "clearingPrice";
    public static final String FIELD_WEIGHT = "weight";
    public static final String FIELD_LENGTH = "length";
    public static final String FIELD_WIDTH = "width";
    public static final String FIELD_HEIGHT = "height";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_AUDIT_IDEA = "auditIdea";
    public static final String FIELD_AUDIT_COMMIT_DATE = "auditCommitDate";
    public static final String FIELD_AUDIT_COMMIT_BY = "auditCommitBy";

    /**
     * 套餐标识：1-套餐
     */
    @Transient
    public static final Integer SET_FLAG_TRUE = 1;
    /**
     * 草稿
     */
    @Transient
    public static final Integer STATUS_DRAFT = 0;
    /**
     * 被驳回
     */
    @Transient
    public static final Integer STATUS_REJECTED = 1;
    /**
     * 审核中
     */
    @Transient
    public static final Integer STATUS_UNDER_REVIEW = 2;
    /**
     * 已生效
     */
    @Transient
    public static final Integer STATUS_TAKE_EFFECT = 3;
    /**
     * 已生效(修改中)
     */
    @Transient
    public static final Integer STATUS_REVISING = 4;
    /**
     * 已生效(已驳回)
     */
    @Transient
    public static final Integer STATUS_TAKE_EFFECT_REJECTED = 5;



    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("id")
    @Id
    @GeneratedValue
    private Long id;
    @ApiModelProperty(value = "商品id")
    private Long spuId;
    @ApiModelProperty(value = "skuCode")
    private String skuCode;
    @ApiModelProperty(value = "显示标志，1显示，0不显示", required = true)
    @NotNull
    private Integer showFlag;
    @ApiModelProperty("套餐标识：0-单品，1-套餐")
    private Integer setFlag;
    @ApiModelProperty(value = "唯一性校验")
    private String md5CheckValue;
    @ApiModelProperty(value = "sku名称")
    private String skuName;
    @ApiModelProperty(value = "成本价", required = true)
    @NotNull
    private BigDecimal costPrice;
    @ApiModelProperty(value = "销售价", required = true)
    @NotNull
    private BigDecimal salesPrice;
    @ApiModelProperty(value = "活动价", required = true)
    @NotNull
    private BigDecimal activityPrice;
    @ApiModelProperty(value = "市场价", required = true)
    @NotNull
    private BigDecimal marketPrice;
    @ApiModelProperty(value = "分销价", required = true)
    @NotNull
    private BigDecimal distributionPrice;
    @ApiModelProperty(value = "批发价", required = true)
    @NotNull
    private BigDecimal wholesalePrice;
    @ApiModelProperty(value = "结算价", required = true)
    @NotNull
    private BigDecimal clearingPrice;
    @ApiModelProperty(value = "商品重量", required = true)
    @NotNull
    private BigDecimal weight;
    @ApiModelProperty(value = "商品长度", required = true)
    @NotNull
    private BigDecimal length;
    @ApiModelProperty(value = "商品宽度", required = true)
    @NotNull
    private BigDecimal width;
    @ApiModelProperty(value = "商品高度", required = true)
    @NotNull
    private BigDecimal height;
    @ApiModelProperty(value = "状态 0草稿1审核中 2被驳回 3已生效 4已生效(修改中) 5已生效(被驳回)", required = true)
    @NotNull
    private Integer status;
    @ApiModelProperty(value = "审核意见")
    private String auditIdea;
    @ApiModelProperty(value = "审核提交时间")
    private Date auditCommitDate;
    @ApiModelProperty(value = "最后提交人id")
    private Long auditCommitBy;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------
    @ApiModelProperty(value = "spu类别")
    @Transient
    private Integer spuType;
    @ApiModelProperty("原始属性列表")
    @Transient
    private List<AttributeDTO> skuAttributeList;
    @ApiModelProperty("附加属性列表")
    @Transient
    private List<AttributeDTO> otherAttributeList;
    @ApiModelProperty("套餐信息 sku明细")
    @Transient
    private List<SkuPackage>skuDetailList;
}
