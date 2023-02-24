package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-04-29
 * @description:
 */
@Data
public class UpdateWarehouseDTO {

	@Encrypt
	private Long id;

	@ApiModelProperty(value = "收货仓库")
	@Encrypt
	private Long receivingWarehouseId;
	@ApiModelProperty(value = "收货仓库编码")
	private String receivingWarehouseCode;
	@ApiModelProperty(value = "收货仓库名称")
	private String receivingWarehouseName;
}
