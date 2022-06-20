package org.skyer.goods.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.skyer.starter.keyencrypt.core.Encrypt;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 商品分类属性值表
 *
 * @author xurongfa 2021-12-24 09:12:57
 */
@ApiModel("商品分类属性值表")
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Table(name = "sgoo_category_attribute_value")
public class CategoryAttributeValue {

    public static final String FIELD_ID = "id";
    public static final String FIELD_ATTRIBUTE_ID = "attributeId";
    public static final String FIELD_VALUE = "value";
    public static final String FIELD_SORT = "sort";

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
    @ApiModelProperty(value = "属性id sgoo_category_attribute.id",required = true)
    @NotNull
    private Long attributeId;

	@ApiModelProperty(value = "属性值")
    private String value;

	@ApiModelProperty(value = "排序")
    private Long sort;

	//
    // 非数据库字段
    // ------------------------------------------------------------------------------


}
