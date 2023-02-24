package org.skyer.goods.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.skyer.starter.keyencrypt.core.Encrypt;

import java.util.List;

/**
 * 商品分类属性表
 *
 * @author xurongfa 2021-12-24 09:12:57
 */
@ApiModel("商品分类属性表")
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_category_attribute")
public class CategoryAttribute {

    public static final String FIELD_ID = "id";
    public static final String FIELD_CATEGORY_ID = "categoryId";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_SORT = "sort";
    public static final String FIELD_FORM_TYPE = "formType";
    public static final String FIELD_SKU_FLAG = "skuFlag";
    public static final String FIELD_STATUS_FLAG = "statusFlag";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_SOURCE_TYPE = "sourceType";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @Encrypt
    @ApiModelProperty("id")
    @Id
    @GeneratedValue
    private Long id;

    @Encrypt
    @ApiModelProperty(value = "分类id sgoo_category.id",required = true)
    @NotNull
    private Long categoryId;

	@ApiModelProperty(value = "属性名称")
    private String name;

	@ApiModelProperty(value = "属性编码")
    private String code;

	@ApiModelProperty(value = "排序")
    private Long sort;

    @ApiModelProperty(value = "表单方式 1单选2复选3文本4下拉 默认3",required = true)
    @NotNull
    private Integer formType;

    @ApiModelProperty(value = "是否用于生成sku 1是 0 否 默认0",required = true)
    @NotNull
    private Boolean skuFlag;

    @ApiModelProperty(value = "状态 1启用 0禁用 默认1",required = true)
    @NotNull
    private Boolean statusFlag;

	@ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "来源类型", required = true)
    private Integer sourceType;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------
    @ApiModelProperty(value = "属性值", hidden = true)
    @Transient
    private List<CategoryAttributeValue> attributeValueList;
}
