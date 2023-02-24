package org.skyer.goods.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.List;

import org.skyer.goods.domain.entity.SpuAttributeValue;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2021-12-28
 * @description:
 */
@Data
@ApiModel("spu属性列表入参")
public class AttrListRequestDTO {

	@ApiModelProperty("id")
	@Encrypt
	private Long id;

	@ApiModelProperty("属性编码")
	private String code;

	@ApiModelProperty("是否用于生产sku")
	private Boolean isSku;

	@ApiModelProperty("状态")
	private Integer status;

	@ApiModelProperty(value = "1-系统默认属性 2-分类属性 0-商品属性   3附加属性")
	private Integer type;

	@ApiModelProperty(value = "属性类别 1单选2复选3文本4下拉")
	private Integer submissionsType;

	@ApiModelProperty("属性名称")
	@NotBlank(message = "属性名称不可为空")
	private String attributeName;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("排序")
	private Integer sort;

	@ApiModelProperty("属性值")
	private List<SpuAttributeValue> values;
}
