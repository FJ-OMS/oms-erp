package org.skyer.goods.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-01-11
 * @description:
 */
@Data
public class SpuEsModel {

	private Long id;

	@ApiModelProperty(value = "商品名称")
	private String name;

	@ApiModelProperty(value = "spu")
	private String spuCode;

	@ApiModelProperty(value = "类型1.实物2虚拟")
	private Integer type;

	@ApiModelProperty("套餐标识：0-单品，1-套餐")
	private Integer setFlag;

	@ApiModelProperty(value = "分类id")
	private Long categoryId;
}
