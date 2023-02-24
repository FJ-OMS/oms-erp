package org.skyer.order.api.vo;

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
public class RefundLogisticsInfoVO {

	@ApiModelProperty(value = "物流信息")
	private List<String> logisticsList;

	@ApiModelProperty(value = "收货仓库")
	@Encrypt
	private Long receivingWarehouseId;
	@ApiModelProperty(value = "收货仓库名称")
	private String receivingWarehouseName;
	@ApiModelProperty(value = "仓库收货状态")
	private String receivingWarehouseStatus;
	@ApiModelProperty(value = "入库通知单Code")
	private String warehousingNotice;
	@ApiModelProperty(value = "入库结果单Code")
	private String warehousingResult;
}
