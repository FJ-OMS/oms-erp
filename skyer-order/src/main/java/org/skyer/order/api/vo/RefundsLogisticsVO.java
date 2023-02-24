package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-03-21
 * @description:
 */
@Data
public class RefundsLogisticsVO {
	@Encrypt
	private Long id;

	@ApiModelProperty(value = "退货物流公司id")
	@Encrypt
	private Long logisticsCompanyId;
	@ApiModelProperty(value = "退货物流公司名称")
	private String logisticsCompanyName;
	@ApiModelProperty(value = "退货物流单号")
	private String logisticsNo;
}
