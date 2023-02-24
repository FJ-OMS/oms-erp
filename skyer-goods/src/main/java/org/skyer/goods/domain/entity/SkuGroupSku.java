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

import java.math.BigDecimal;

/**
 * Sku组合和sku关联表
 *
 * @author wushaochuan 2021-08-13 09:55:04
 */
@ApiModel("Sku组合和sku关联表")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_sku_group_sku")
public class SkuGroupSku extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_SKU_ID = "skuId";
    public static final String FIELD_SKU_GROUP_ID = "skuGroupId";


    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


	public SkuGroupSku() {
		this.deleteFlag = 0;
	}

	@ApiModelProperty("id")
    @Id
    @GeneratedValue
    private Long id;
   @ApiModelProperty(value = "删除状态")    
    private Integer deleteFlag;
   @ApiModelProperty(value = "租户Id")    
    private Long tenantId;
   @ApiModelProperty(value = "skuid")    
    private Long skuId;
   @ApiModelProperty(value = "sku组合商品")    
    private Long skuGroupId;
   @ApiModelProperty(value = "数量")
   private BigDecimal num;
   @ApiModelProperty("spuId")
   private Long spuId;

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
     * @return skuid
     */
	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
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

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}
}
