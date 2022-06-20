package org.skyer.stock.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: lzh
 * @date: 2021-06-25
 * @description:
 */
@Data
public class AreaInfoDetailsVO {

	private Long levelThreeAreaCode;
	private Long levelTwoAreaCode;
	private Long levelOneAreaCode;

	private String levelThreeAreaName;
	private String levelTwoAreaName;
	private String levelOneAreaName;
	@ApiModelProperty(value = "经度")
	private BigDecimal lng;
	@ApiModelProperty(value = "纬度")
	private BigDecimal lat;
}
