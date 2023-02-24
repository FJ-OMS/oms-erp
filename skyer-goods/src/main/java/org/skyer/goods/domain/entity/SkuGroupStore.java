package org.skyer.goods.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import org.skyer.goods.infra.constant.ObjectConstant;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Sku组合和店铺表
 *
 * @author wushaochuan 2021-08-13 09:55:04
 */
@ApiModel("Sku组合和店铺表")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_sku_group_store")
public class SkuGroupStore extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_SPU_ID = "spuId";
    public static final String FIELD_STORE_ID = "storeId";
    public static final String FIELD_SKU_GROUP_ID = "skuGroupId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


	public SkuGroupStore() {
		this.deleteFlag = ObjectConstant.DeleteFlag.NO;
	}

	@ApiModelProperty("id")
    @Id
    @GeneratedValue
    private Long id;
   @ApiModelProperty(value = "删除状态")    
    private Integer deleteFlag;
   @ApiModelProperty(value = "租户Id")    
    private Long tenantId;
   @ApiModelProperty(value = "商品id")    
    private Long spuId;
   @ApiModelProperty(value = "店铺id")    
    private Long storeId;
   @ApiModelProperty(value = "sku组合商品")    
    private Long skuGroupId;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    //
    // getter/setter
    // ------------------------------------------------------------------------------

    /**
     * @return id
     */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    /**
     * @return 删除状态
     */
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
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
     * @return 店铺id
     */
	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
    /**
     * @return sku组合商品
     */
	public Long getSkuGroupId() {
		return skuGroupId;
	}

	public void setSkuGroupId(Long skuGroupId) {
		this.skuGroupId = skuGroupId;
	}

}
