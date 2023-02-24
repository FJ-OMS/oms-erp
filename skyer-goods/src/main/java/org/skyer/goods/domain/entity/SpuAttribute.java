package org.skyer.goods.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
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

import java.util.List;

/**
 * 属性表
 *
 * @author linzhuanghuang@ebc.com 2021-12-28 16:40:03
 */
@ApiModel("属性表")
@ModifyAudit
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_spu_attribute")
@Data
public class SpuAttribute extends AuditDomain {

	public static final String FIELD_ID = "id";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_SORT = "sort";
	public static final String FIELD_CODE = "code";
	public static final String FIELD_REMARK = "remark";
	public static final String FIELD_DELETE_FLAG = "deleteFlag";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_IS_SKU = "isSku";
	public static final String FIELD_STATUS = "status";
	public static final String FIELD_SUBMISSIONS_TYPE = "submissionsType";
	public static final String FIELD_SPU_ID = "spuId";

	//
	// 业务方法(按public protected private顺序排列)
	// ------------------------------------------------------------------------------

	//
	// 数据库字段
	// ------------------------------------------------------------------------------


	@ApiModelProperty("id")
	@Id
	@GeneratedValue
	@Encrypt
	private Long id;
	@ApiModelProperty(value = "属性名称")
	private String name;
	@ApiModelProperty(value = "排序")
	private Integer sort;
	@ApiModelProperty(value = "编码")
	private String code;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "删除状态")
	private Integer deleteFlag;
	@ApiModelProperty(value = "1-系统默认属性 2-分类属性 0-商品属性   3附加属性")
	private Integer type;
	@ApiModelProperty(value = "是否用于生成sku")
	private Boolean isSku;
	@ApiModelProperty(value = "状态")
	private Integer status;
	@ApiModelProperty(value = "属性类别 1单选2复选3文本4下拉")
	private Integer submissionsType;
	@ApiModelProperty(value = "", required = true)
	@NotNull
	private Long spuId;

	@Transient
	private List<SpuAttributeValue> values;
}
