package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.skyer.common.SkyerCacheKey;
import org.skyer.core.cache.CacheValue;
import org.skyer.core.cache.Cacheable;
import org.skyer.goods.api.dto.AttributeDTO;
import org.skyer.starter.keyencrypt.core.Encrypt;

@Data
public class GoodsSkuAuditVO implements Cacheable {

    @ApiModelProperty("id")
    @Encrypt
    private Long id;

    @ApiModelProperty(value = "商品id")
    @Encrypt
    private Long spuId;

    @ApiModelProperty(value = "商品名称")
    private String spuName;

    @ApiModelProperty(value = "spu")
    private String spuCode;

    @ApiModelProperty(value = "skuCode")
    private String skuCode;

    @ApiModelProperty(value = "分类名")
    String categoryName;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty("素材名称")
    private String materialName;

    @ApiModelProperty("主图")
    private String mainImage;

    @ApiModelProperty("详情图")
    private String detailImage;

    @ApiModelProperty("详情内容")
    private String detailHtml;

    @ApiModelProperty(value = "显示标志，1显示，0不显示", required = true)
    private Integer showFlag;

    @ApiModelProperty(value = "唯一性校验")
    private String md5CheckValue;

    @ApiModelProperty(value = "sku名称")
    private String skuName;

    @ApiModelProperty(value = "商品重量", required = true)
    private BigDecimal weight;

    @ApiModelProperty(value = "商品长度", required = true)
    private BigDecimal length;

    @ApiModelProperty(value = "商品宽度", required = true)
    private BigDecimal width;

    @ApiModelProperty(value = "商品高度", required = true)
    private BigDecimal height;

    @ApiModelProperty(value = "状态 0草稿1审核中 2被驳回 3已生效 4已生效(修改中)", required = true)
    private Integer status;

    @ApiModelProperty("价格集")
    private GoodsSkuAuditPriceVO priceVO;

    @ApiModelProperty("原始属性列表")
    private List<AttributeDTO> skuAttributeList;

    @ApiModelProperty("附加属性列表")
    private List<AttributeDTO> otherAttributeList;

    @ApiModelProperty(value = "审核意见")
    private String auditIdea;

    @ApiModelProperty(value = "审核提交时间")
    private Date auditCommitDate;

    @ApiModelProperty(value = "最后提交人id")
    private Long auditCommitBy;

    @ApiModelProperty(value = "最后提交人姓名")
    @CacheValue(
            key = SkyerCacheKey.USER,
            primaryKey = "auditCommitBy",
            searchKey = "realName",
            structure = CacheValue.DataStructure.MAP_OBJECT
    )
    private String auditCommitName;

    @ApiModelProperty("套餐信息 sku明细")
    private List<SkuPackageVO> skuDetailList;
}
