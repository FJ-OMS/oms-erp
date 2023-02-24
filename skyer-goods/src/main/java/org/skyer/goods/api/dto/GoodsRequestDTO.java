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
@ApiModel("添加编辑商品参数")
public class GoodsRequestDTO {
	@ApiModelProperty("主键id")
	@Encrypt
	private Long id;

	@ApiModelProperty("商品编码")
	@NotBlank(message = "编码不能为空")
	private String spuCode;

	@ApiModelProperty("类别")
	@NotNull(message = "类别不能为空")
	private Integer type;

	@ApiModelProperty("商品名称")
	@NotBlank(message = "名称不能为空")
	private String name;

	@ApiModelProperty("类别id")
	@NotNull(message = "商品类别不能为空")
	@Encrypt
	private Long categoryId;

	@ApiModelProperty("品牌Id")
	@Encrypt
	private Long brandId;

	@ApiModelProperty("素材Id")
	@Encrypt
	private Long materialId;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("状态")
	private Integer status;

	@ApiModelProperty("套餐标识：0-单品，1-套餐")
	private Integer setFlag;

	@ApiModelProperty("属性")
	private List<AttrListRequestDTO> attrs;
}
