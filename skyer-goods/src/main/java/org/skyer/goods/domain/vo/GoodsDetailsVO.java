package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import org.skyer.goods.api.dto.AttrListRequestDTO;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2021-12-28
 * @description:
 */
@Data
@ApiModel(description = "查询商品详情")
public class GoodsDetailsVO {
	@ApiModelProperty("主键")
	@Encrypt
	private Long id;

	@ApiModelProperty("商品编码")
	private String spuCode;

	@ApiModelProperty("商品名称")
	private String name;

	@ApiModelProperty("商品类别")
	private Integer type;

	@ApiModelProperty("分类id")
	@Encrypt
	private Long categoryId;

	@ApiModelProperty("分类名称")
	private String categoryName;

	@ApiModelProperty("品牌名称")
	private String brandName;

	@ApiModelProperty("素材id")
	@Encrypt
	private Long materialId;

	@ApiModelProperty("素材名称")
	private String materialName;

	@ApiModelProperty("商品主图")
	private List<String> mainImage;

	@ApiModelProperty("商品详情图")
	private List<String> imageList;

	@ApiModelProperty("描述")
	private String detailHtml;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty("套餐标识：0-单品，1-套餐")
	private Integer setFlag;

	@ApiModelProperty(value = "品牌id")
	@Encrypt
	private Long brandId;
	@ApiModelProperty("商品属性值")
	private List<AttrListRequestDTO> attrLists;

}
