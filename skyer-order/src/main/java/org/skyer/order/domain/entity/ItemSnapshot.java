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
 * 内部订单商品明细快照
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@ApiModel("内部订单商品明细快照")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sord_item_snapshot")
public class ItemSnapshot extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_OUTER_ORDER_NO = "outerOrderNo";
    public static final String FIELD_INNER_ORDER_NO = "innerOrderNo";
    public static final String FIELD_SPU_ID = "spuId";
    public static final String FIELD_SPU_CODE = "spuCode";
    public static final String FIELD_SPU_NAME = "spuName";
    public static final String FIELD_SPU_TYPE = "spuType";
    public static final String FIELD_SKU_ID = "skuId";
    public static final String FIELD_SKU_CODE = "skuCode";
    public static final String FIELD_SKU_NAME = "skuName";
    public static final String FIELD_BRAND_INFO = "brandInfo";
    public static final String FIELD_MATERIAL_INFO = "materialInfo";
    public static final String FIELD_ATTRIBUTE_INFO = "attributeInfo";
    public static final String FIELD_CATEGORY_INFO = "categoryInfo";
    public static final String FIELD_COST_PRICE = "costPrice";
    public static final String FIELD_SALE_PRICE = "salePrice";
    public static final String FIELD_PROMOTION_PRICE = "promotionPrice";
    public static final String FIELD_DISTRIBUTE_PRICE = "distributePrice";
    public static final String FIELD_MARKET_PRICE = "marketPrice";
    public static final String FIELD_BATCH_PRICE = "batchPrice";
    public static final String FIELD_SETTLE_PRICE = "settlePrice";
    public static final String FIELD_WEIGHT = "weight";
    public static final String FIELD_LENGTH = "length";
    public static final String FIELD_WIDTH = "width";
    public static final String FIELD_HEIGHT = "height";
    public static final String FIELD_SET_FLAG = "setFlag";
    public static final String FIELD_GIFT_FLAG = "giftFlag";
    public static final String FIELD_MULTI_FLAG = "multiFlag";
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
   @ApiModelProperty(value = "平台订单号")    
    private String outerOrderNo;
   @ApiModelProperty(value = "内部订单号")    
    private String innerOrderNo;
   @ApiModelProperty(value = "商品id")    
    private Long spuId;
   @ApiModelProperty(value = "商品编码")    
    private String spuCode;
   @ApiModelProperty(value = "商品名称")    
    private String spuName;
   @ApiModelProperty(value = "商品类型1.实物2虚拟")    
    private Integer spuType;
   @ApiModelProperty(value = "sku id")    
    private Long skuId;
   @ApiModelProperty(value = "sku编码")    
    private String skuCode;
   @ApiModelProperty(value = "sku名称")    
    private String skuName;
   @ApiModelProperty(value = "品牌信息")    
    private String brandInfo;
   @ApiModelProperty(value = "素材信息")    
    private String materialInfo;
   @ApiModelProperty(value = "属性信息")    
    private String attributeInfo;
   @ApiModelProperty(value = "分类信息")    
    private String categoryInfo;
   @ApiModelProperty(value = "成本价，计价单位：元，保留小数点后2位")    
    private BigDecimal costPrice;
   @ApiModelProperty(value = "销售价，计价单位：元，保留小数点后2位")    
    private BigDecimal salePrice;
   @ApiModelProperty(value = "活动价（促销价），计价单位：元，保留小数点后2位")    
    private BigDecimal promotionPrice;
   @ApiModelProperty(value = "分销价，计价单位：元，保留小数点后2位")    
    private BigDecimal distributePrice;
   @ApiModelProperty(value = "市场价，计价单位：元，保留小数点后2位")    
    private BigDecimal marketPrice;
   @ApiModelProperty(value = "批发价，计价单位：元，保留小数点后2位")    
    private BigDecimal batchPrice;
   @ApiModelProperty(value = "结算价，计价单位：元，保留小数点后2位")    
    private BigDecimal settlePrice;
   @ApiModelProperty(value = "商品重量")    
    private BigDecimal weight;
   @ApiModelProperty(value = "商品长度")    
    private BigDecimal length;
   @ApiModelProperty(value = "商品宽度")    
    private BigDecimal width;
   @ApiModelProperty(value = "商品高度")    
    private BigDecimal height;
   @ApiModelProperty(value = "套餐标识：0-单品，1-套餐")    
    private Integer setFlag;
   @ApiModelProperty(value = "赠品标识：0-卖品，1-赠品")    
    private Integer giftFlag;
   @ApiModelProperty(value = "子母件标识：0-普通单品，1-子母件")    
    private Integer multiFlag;
   @ApiModelProperty(value = "系统字段：删除标识")    
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
     * @return 商品id
     */
	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}
    /**
     * @return 商品编码
     */
	public String getSpuCode() {
		return spuCode;
	}

	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}
    /**
     * @return 商品名称
     */
	public String getSpuName() {
		return spuName;
	}

	public void setSpuName(String spuName) {
		this.spuName = spuName;
	}
    /**
     * @return 商品类型1.实物2虚拟
     */
	public Integer getSpuType() {
		return spuType;
	}

	public void setSpuType(Integer spuType) {
		this.spuType = spuType;
	}
    /**
     * @return sku id
     */
	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
    /**
     * @return sku编码
     */
	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
    /**
     * @return sku名称
     */
	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
    /**
     * @return 品牌信息
     */
	public String getBrandInfo() {
		return brandInfo;
	}

	public void setBrandInfo(String brandInfo) {
		this.brandInfo = brandInfo;
	}
    /**
     * @return 素材信息
     */
	public String getMaterialInfo() {
		return materialInfo;
	}

	public void setMaterialInfo(String materialInfo) {
		this.materialInfo = materialInfo;
	}
    /**
     * @return 属性信息
     */
	public String getAttributeInfo() {
		return attributeInfo;
	}

	public void setAttributeInfo(String attributeInfo) {
		this.attributeInfo = attributeInfo;
	}
    /**
     * @return 分类信息
     */
	public String getCategoryInfo() {
		return categoryInfo;
	}

	public void setCategoryInfo(String categoryInfo) {
		this.categoryInfo = categoryInfo;
	}
    /**
     * @return 成本价，计价单位：元，保留小数点后2位
     */
	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
    /**
     * @return 销售价，计价单位：元，保留小数点后2位
     */
	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
    /**
     * @return 活动价（促销价），计价单位：元，保留小数点后2位
     */
	public BigDecimal getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(BigDecimal promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
    /**
     * @return 分销价，计价单位：元，保留小数点后2位
     */
	public BigDecimal getDistributePrice() {
		return distributePrice;
	}

	public void setDistributePrice(BigDecimal distributePrice) {
		this.distributePrice = distributePrice;
	}
    /**
     * @return 市场价，计价单位：元，保留小数点后2位
     */
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
    /**
     * @return 批发价，计价单位：元，保留小数点后2位
     */
	public BigDecimal getBatchPrice() {
		return batchPrice;
	}

	public void setBatchPrice(BigDecimal batchPrice) {
		this.batchPrice = batchPrice;
	}
    /**
     * @return 结算价，计价单位：元，保留小数点后2位
     */
	public BigDecimal getSettlePrice() {
		return settlePrice;
	}

	public void setSettlePrice(BigDecimal settlePrice) {
		this.settlePrice = settlePrice;
	}
    /**
     * @return 商品重量
     */
	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
    /**
     * @return 商品长度
     */
	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}
    /**
     * @return 商品宽度
     */
	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}
    /**
     * @return 商品高度
     */
	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}
    /**
     * @return 套餐标识：0-单品，1-套餐
     */
	public Integer getSetFlag() {
		return setFlag;
	}

	public void setSetFlag(Integer setFlag) {
		this.setFlag = setFlag;
	}
    /**
     * @return 赠品标识：0-卖品，1-赠品
     */
	public Integer getGiftFlag() {
		return giftFlag;
	}

	public void setGiftFlag(Integer giftFlag) {
		this.giftFlag = giftFlag;
	}
    /**
     * @return 子母件标识：0-普通单品，1-子母件
     */
	public Integer getMultiFlag() {
		return multiFlag;
	}

	public void setMultiFlag(Integer multiFlag) {
		this.multiFlag = multiFlag;
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
