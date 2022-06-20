package org.skyer.channel.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

import org.skyer.common.SkyerCacheKey;
import org.skyer.core.cache.CacheValue;
import org.skyer.core.cache.Cacheable;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2021-06-18
 * @description:
 */
@Data
public class DealerVO implements Cacheable {

	@Encrypt
	private Long id;

	private String dealerCode;
	private String dealerName;
	private Boolean status;
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
