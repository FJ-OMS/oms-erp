package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lzh
 * @date: 2022-04-28
 * @description:
 */
@Data
public class WarehouseWarrantResultVO {
	@ApiModelProperty(value = "前置业务单号")
	private String frontRecordCode;

	@ApiModelProperty(value = "前置业务单id")
	private Long frontRecordId;

	@ApiModelProperty(value = "入库通知单id")
	private Long warehouseWarrantId;

	@ApiModelProperty(value = "入库通知单号")
	private String warehouseWarrantCode;

	@ApiModelProperty(value = "入库结果单id")
	private Long warehouseResultId;

	@ApiModelProperty(value = "入库结果单号")
	private String warehouseResultCode;

	@ApiModelProperty(value = "通知单ID")
	private Long adjustRecordId;
}
