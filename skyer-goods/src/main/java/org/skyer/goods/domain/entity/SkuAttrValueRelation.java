package org.skyer.goods.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品sku和属性值关系表
 *
 * @author chenzz 2021-07-06 10:35:44
 */
@ApiModel("商品sku和属性值关系表")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_sku_attr_value_relation")
public class SkuAttrValueRelation extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_GOODS_SKU_ID = "goodsSkuId";
    public static final String FIELD_SKU = "sku";
    public static final String FIELD_ATTRIBUTE_VALUE_ID = "attributeValueId";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_TENANT_ID = "tenantId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


	public SkuAttrValueRelation() {
		this.deleteFlag = false;
	}

	@ApiModelProperty("id主键")
    @Id
    @GeneratedValue
    private Long id;
   @ApiModelProperty(value = "sku表id")    
    private Long goodsSkuId;
   @ApiModelProperty(value = "sku")    
    private String sku;
   @ApiModelProperty(value = "属性值表id")    
    private Long attributeValueId;
   @ApiModelProperty(value = "删除状态")    
    private Boolean deleteFlag;
   @ApiModelProperty(value = "租户Id")    
    private Long tenantId;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return id主键
     */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    /**
     * @return sku表id
     */
	public Long getGoodsSkuId() {
		return goodsSkuId;
	}

	public void setGoodsSkuId(Long goodsSkuId) {
		this.goodsSkuId = goodsSkuId;
	}
    /**
     * @return sku
     */
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
    /**
     * @return 属性值表id
     */
	public Long getAttributeValueId() {
		return attributeValueId;
	}

	public void setAttributeValueId(Long attributeValueId) {
		this.attributeValueId = attributeValueId;
	}
    /**
     * @return 删除状态
     */
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

    /**
     * @return 租户Id
     */
	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag == null ? false : deleteFlag;
	}
}
