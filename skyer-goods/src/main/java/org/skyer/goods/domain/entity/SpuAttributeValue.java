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
import org.skyer.starter.keyencrypt.core.Encrypt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 属性值表
 *
 * @author linzhuanghuang@ebc.com 2021-12-28 16:40:03
 */
@ApiModel("属性值表")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_spu_attribute_value")
@Data
public class SpuAttributeValue extends AuditDomain {

	public static final String FIELD_ID = "id";
	public static final String FIELD_ATTRIBUTE_ID = "attributeId";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_DELETE_FLAG = "deleteFlag";
	public static final String FIELD_SORT = "sort";
	public static final String FIELD_SPU_ID = "spuId";

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
	@ApiModelProperty(value = "属性id")
	private Long attributeId;
	@ApiModelProperty(value = "属性值")
	private String value;
	@ApiModelProperty(value = "删除状态")
	private Integer deleteFlag;
	@ApiModelProperty(value = "排序")
	private Integer sort;
	@ApiModelProperty(value = "spuId")
	private Long spuId;

}
