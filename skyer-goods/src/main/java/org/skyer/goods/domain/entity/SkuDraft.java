package org.skyer.goods.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import lombok.Data;

import org.skyer.mybatis.domain.AuditDomain;

import java.math.BigDecimal;

import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品规格草稿表
 *
 * @author zhanglongbing@ebc.com 2021-12-29 11:12:15
 */
@ApiModel("商品规格草稿表")
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_sku_draft")
public class SkuDraft {

    public static final String FIELD_SKU_ID = "skuId";
    public static final String FIELD_SKU_CODE = "skuCode";
    public static final String FIELD_SHOW_FLAG = "showFlag";
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

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("id")
    @Id
    @GeneratedValue
    private Long skuId;
    @ApiModelProperty(value = "sku")
    private String skuCode;
    @ApiModelProperty(value = "显示标志，1显示，0不显示", required = true)
    @NotNull
    private Integer showFlag;
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

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------


}
