package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.goods.domain.entity.SkuAttributeValue;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品规格表
 *
 * @author chenzz 2021-07-06 10:35:43
 */
@ApiModel("商品SKU")
@Data
public class GoodsSkuPageVO {

    @ApiModelProperty(value = "主键id")
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "商品id")
    @Encrypt
    private Long spuId;

    @ApiModelProperty(value = "商品名称")
    private String spuName;

    @ApiModelProperty(value = "spuCode")
    private String spuCode;

    @ApiModelProperty(value = "spu类别")
    private Integer spuType;

    @ApiModelProperty(value = "spu分类")
    private String categoryCode;

    @ApiModelProperty("套餐标识：0-单品，1-套餐")
    private Integer setFlag;

    @ApiModelProperty(value = "素材id")
    @Encrypt
    private Long materialId;

    @ApiModelProperty(value = "素材名称")
    private String materialName;

    @ApiModelProperty(value = "素材")
    private List<String> materialMainImages;

    @ApiModelProperty(value = "sku")
    private String skuCode;

    @ApiModelProperty(value = "sku名称")
    private String skuName;

    private String categoryName;

    @ApiModelProperty(value = "显示标志，1显示，0不显示")
    private Integer showFlag;

    private String goodsRemark;

    @ApiModelProperty(value = "状态 0草稿1审核中 2被驳回 3已生效 4已生效(修改中)")
    private Integer status;

    @ApiModelProperty(value = "成本价")
    private BigDecimal costPrice;
    @ApiModelProperty(value = "销售价")
    private BigDecimal salesPrice;
    @ApiModelProperty(value = "活动价")
    private BigDecimal activityPrice;
    @ApiModelProperty(value = "市场价")
    private BigDecimal marketPrice;
    @ApiModelProperty(value = "分销价")
    private BigDecimal distributionPrice;
    @ApiModelProperty(value = "批发价")
    private BigDecimal wholesalePrice;
    @ApiModelProperty(value = "结算价")
    private BigDecimal clearingPrice;
    @ApiModelProperty(value = "商品重量")
    private BigDecimal weight;
    @ApiModelProperty(value = "商品长度")
    private BigDecimal length;
    @ApiModelProperty(value = "商品宽度")
    private BigDecimal width;
    @ApiModelProperty(value = "商品高度")
    private BigDecimal height;
    @ApiModelProperty(value = "审核意见")
    private String auditIdea;
    @ApiModelProperty(value = "审核提交时间")
    private Date auditCommitDate;

    @ApiModelProperty("原始属性列表")
    private List<SkuAttributeValue> skuAttributeList;

    @ApiModelProperty("附加属性列表")
    private List<SkuAttributeValue> otherAttributeList;


}
