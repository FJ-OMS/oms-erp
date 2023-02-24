package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-04-02
 * @description:
 */
@Data
public class ItemConfirmDTO {

	@ApiModelProperty(value = "商品id")
	private List<IdListDTO> itemIds;

	@Encrypt
	@ApiModelProperty(value = "商品行ID")
	private List<Long> itemLineIds;

	@ApiModelProperty(value = "内部订单号")
	private String innerOrderNo;
}
