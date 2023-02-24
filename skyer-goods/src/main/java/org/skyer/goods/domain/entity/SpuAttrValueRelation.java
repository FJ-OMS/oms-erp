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
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * 商品和属性值关联关系
 *
 * @author wushaochuan 2021-07-19 09:18:33
 */
@ApiModel("商品和属性值关联关系")
@VersionAudit
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_spu_attr_value_relation")
public class SpuAttrValueRelation extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_GOODS_ID = "spuId";
    public static final String FIELD_ATTRIBUTE_VALUE_ID = "attributeValueId";
    public static final String FIELD_ATTRIBUTE_ID = "attributeId";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_ATTRIVUTE_INPUT_VALUE = "attributeInputValue";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @ApiModelProperty("id主键")
    @Id
    @GeneratedValue
	@Encrypt
    private Long id;
   @ApiModelProperty(value = "商品id")
   @Encrypt
    private Long spuId;
   @ApiModelProperty(value = "属性值表id")
   @Encrypt
    private Long attributeValueId;
   @ApiModelProperty(value = "属性id")
   @Encrypt
    private Long attributeId;
   @ApiModelProperty(value = "删除状态")    
    private Integer deleteFlag;
   @ApiModelProperty(value = "租户Id")    
    private Long tenantId;
   @ApiModelProperty(value = "用户填写的属性值")    
    private String attributeInputValue;

	@ApiModelProperty(value = "属性code")
	private String attributeCode;
   @ApiModelProperty(value = "属性类别类别 1单选2复选3文本4下拉")
    private Integer type;

	public SpuAttrValueRelation() {
		this.deleteFlag = 0;
	}
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
     * @return 商品id
     */
	public Long getSpuId() {
		return spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
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
     * @return 属性id
     */
	public Long getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}
    /**
     * @return 删除状态
     */
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag == null ? 0 : deleteFlag;
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
     * @return 用户填写的属性值
     */
	public String getAttributeInputValue() {
		return attributeInputValue;
	}

	public void setAttributeInputValue(String attributeInputValue) {
		this.attributeInputValue = attributeInputValue;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAttributeCode() {
		return attributeCode;
	}

	public void setAttributeCode(String attributeCode) {
		this.attributeCode = attributeCode;
	}
}
