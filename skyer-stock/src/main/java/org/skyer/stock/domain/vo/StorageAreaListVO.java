package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

import org.skyer.common.SkyerCacheKey;
import org.skyer.core.cache.CacheValue;
import org.skyer.core.cache.Cacheable;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2021-08-20
 * @description:
 */
@Data
public class StorageAreaListVO implements Cacheable {

	@Encrypt
	private Long id;
	@ApiModelProperty(value = "删除状态")
	private Boolean deleteFlag;
	@ApiModelProperty(value = "逻辑仓库ID")
	@Encrypt
	private Long warehouseId;
	@ApiModelProperty(value = "逻辑仓名称")
	private String logicalWarehouseName;
	@ApiModelProperty(value = "逻辑仓状态")
	private Boolean logicalFlag;
	@ApiModelProperty(value = "仓库名称")
	private String name;
	@ApiModelProperty(value = "仓库编码")
	private String code;
	@ApiModelProperty(value = "可用状态：0禁用，1启用")
	private Boolean enableFlag;
	@ApiModelProperty(value = "联系人姓名")
	private String contactName;
	@ApiModelProperty(value = "联系人电话")
	private String contactPhone;
	@ApiModelProperty(value = "省")
	private String provinceName;
	@ApiModelProperty(value = "市")
	private String cityName;
	@ApiModelProperty(value = "区")
	private String districtName;
	@ApiModelProperty(value = "详细地址", required = true)
	@NotBlank
	private String detailAddress;

	private Date creationDate;
	private Long createdBy;
	@ApiModelProperty("创建人用户名")
	@CacheValue(
			key = SkyerCacheKey.USER,
			primaryKey = "createdBy",
			searchKey = "realName",
			structure = CacheValue.DataStructure.MAP_OBJECT
	)
	private String createdName;

}
