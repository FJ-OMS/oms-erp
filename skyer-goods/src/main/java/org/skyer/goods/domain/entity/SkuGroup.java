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
 * Sku组合表
 *
 * @author wushaochuan 2021-08-13 09:55:04
 */
@ApiModel("Sku组合表")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_sku_group")
public class SkuGroup extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_SPU_CODE = "spuCode";
    public static final String FIELD_SKU_GROUP_CODE = "skuGroupCode";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_PRICE = "price";
    public static final String FIELD_MD5_CHECK_VALUE = "md5CheckValue";
    public static final String FIELD_GROUP_NAME = "groupName";

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
   @ApiModelProperty(value = "sku")    
    private String skuGroupCode;
   @ApiModelProperty(value = "删除状态")    
    private Integer deleteFlag;
   @ApiModelProperty(value = "租户Id")    
    private Long tenantId;
   @ApiModelProperty(value = "价格 多个价格使用json串保存")    
    private String price;
   @ApiModelProperty(value = "唯一性校验")    
    private String md5CheckValue;
   @ApiModelProperty(value = "套餐名称")
    private String groupName;
   @ApiModelProperty(value = "是否适用于全部店铺")
   	private Boolean isAllStore;
   @ApiModelProperty("备注")
   private String remark;

	public SkuGroup() {
		this.deleteFlag = ObjectConstant.DeleteFlag.NO;
	}
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
     * @return 商品id
     */
	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
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
     * @return 价格 多个价格使用json串保存
     */
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
    /**
     * @return 唯一性校验
     */
	public String getMd5CheckValue() {
		return md5CheckValue;
	}

	public void setMd5CheckValue(String md5CheckValue) {
		this.md5CheckValue = md5CheckValue;
	}

	public String getSkuGroupCode() {
		return skuGroupCode;
	}

	public void setSkuGroupCode(String skuGroupCode) {
		this.skuGroupCode = skuGroupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Boolean getIsAllStore() {
		return isAllStore;
	}

	public void setIsAllStore(Boolean isAllStore) {
		this.isAllStore = isAllStore;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
