package org.skyer.goods.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.skyer.goods.api.dto.LevelCategoryDTO;
import org.skyer.goods.domain.entity.SkuAttributeValue;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品规格表
 *
 * @author chenzz 2021-07-06 10:35:43
 */
@ApiModel("商品SKU")
@Data
public class GoodsSkuVO {

    @ApiModelProperty(value = "主键id")
    @JsonSerialize(using = ToStringSerializer.class)
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "商品id")
    @JsonSerialize(using = ToStringSerializer.class)
    @Encrypt
    private Long spuId;

    @ApiModelProperty(value = "spu")
    private String spuCode;

    @ApiModelProperty(value = "素材id")
    @JsonSerialize(using = ToStringSerializer.class)
    @Encrypt
    private Long materialId;

    @ApiModelProperty(value = "sku")
    private String skuCode;

    private MaterialVO material;

    /**
     * 商品详情
     */
    private GoodsDetailResVO goodsDetails;


    private String categoryName;


    @ApiModelProperty(value = "显示标志，1显示，0不显示")
    private Integer showFlag;


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

    @ApiModelProperty("原始属性列表")
    private List<SkuAttributeValue> skuAttributeList;

    @ApiModelProperty("附加属性列表")
    private List<SkuAttributeValue> otherAttributeList;


}
