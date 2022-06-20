package org.skyer.stock.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2021-08-17
 * @description:
 */
@Data
public class StorageAreaDTO {

	@Encrypt
	private Long id;
	@ApiModelProperty(value = "租户Id")
	private Long tenantId;
	@ApiModelProperty(value = "状态：true启用，false禁用")
	private Boolean enableFlag;
	@ApiModelProperty(value = "仓储名称")
	private String name;
	@ApiModelProperty(value = "仓储编码")
	private String code;
	@ApiModelProperty(value = "省份编码", required = true)
	@NotBlank
	private Long provinceCode;
	@ApiModelProperty(value = "省份名称", required = true)
	@NotBlank
	private String provinceName;
	@ApiModelProperty(value = "城市编码", required = true)
	@NotBlank
	private Long cityCode;
	@ApiModelProperty(value = "城市名称", required = true)
	@NotBlank
	private String cityName;
	@ApiModelProperty(value = "区县编码", required = true)
	@NotBlank
	private Long districtCode;
	@ApiModelProperty(value = "区县名称", required = true)
	@NotBlank
	private String districtName;
	@ApiModelProperty(value = "详细地址", required = true)
	@NotBlank
	private String detailAddress;
	@ApiModelProperty(value = "经度")
	private String longitude;
	@ApiModelProperty(value = "纬度")
	private String latitude;
	@ApiModelProperty(value = "联系人姓名")
	private String contactName;
	@ApiModelProperty(value = "联系人电话")
	private String contactPhone;
	@ApiModelProperty(value = "描述")
	private String description;
	@ApiModelProperty(value = "是否启用逻辑仓：0禁用，1启用")
	@NotBlank
	private Boolean logicalFlag;
	@ApiModelProperty(value = "逻辑仓名称")
	private String logicalWarehouseName;
}
