package org.skyer.goods.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.skyer.mybatis.annotation.ModifyAudit;
import org.skyer.mybatis.annotation.VersionAudit;
import org.skyer.mybatis.domain.AuditDomain;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 商品分类表
 *
 * @author xurongfa 2021-12-24 09:12:57
 */
@ApiModel("商品分类表")
@VersionAudit
@ModifyAudit
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_category")
public class Category extends AuditDomain {

    public static final String FIELD_ID = "id";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_PARENT_ID = "parentId";
    public static final String FIELD_STATUS_FLAG = "statusFlag";
    public static final String FIELD_LEVEL = "level";
    public static final String FIELD_SUB_FLAG = "subFlag";
    public static final String FIELD_SORT = "sort";
    public static final String FIELD_REMARK = "remark";

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

	@ApiModelProperty(value = "分类编码")
    private String code;

	@ApiModelProperty(value = "分类名称")
    private String name;

    @Encrypt
    @ApiModelProperty(value = "父id 默认0,代表一级",required = true)
    @NotNull
    private Long parentId;

    @ApiModelProperty(value = "状态 1启用 0禁用 默认1",required = true)
    @NotNull
    private Boolean statusFlag;

    @ApiModelProperty(value = "类目等级 一级开始 默认1",required = true)
    @NotNull
    private Integer level;

    @ApiModelProperty(value = "是否叶子类目 0否 1是，默认0",required = true)
    @NotNull
    private Boolean subFlag;

	@ApiModelProperty(value = "排序")
    private Long sort;

	@ApiModelProperty(value = "备注")
    private String remark;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------

    @ApiModelProperty(value = "下级分类", hidden = true)
    @Transient
    private List<Category> children;

    @ApiModelProperty(value = "分类属性", hidden = true)
    @Transient
    private List<CategoryAttribute> attributeList;
}
