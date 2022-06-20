package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-03-16
 * @description:
 */
@Data
public class RefundLogisticsDTO {

	@ApiModelProperty(value = "收货仓库ID")
	@Encrypt
	private Long receivingWarehouseId;
	@ApiModelProperty(value = "收货仓库名称")
	private String receivingWarehouseName;
	@ApiModelProperty(value = "仓库收货状态")
	private String receivingWarehouseStatus;
	@ApiModelProperty(value = "退货物流信息")
	private List<Logistics> logisticsList;

	@Data
	public static class Logistics{
		@ApiModelProperty(value = "退货物流公司id")
		@Encrypt
		private Long returnLogisticsCompanyId;
		@ApiModelProperty(value = "退货物流")
		private String returnLogisticsCompanyName;
		@ApiModelProperty(value = "退货快递单号")
		private String returnLogisticsNo;
	}
}
