package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import org.skyer.order.domain.entity.AfterSaleItem;

/**
 * @author: lzh
 * @date: 2022-03-16
 * @description:
 */
@Data
public class RefundGoodsDTO {

	@ApiModelProperty("关联订单信息")
	private OrderRefundInfoDTO orderRefundInfoDTO;

	@ApiModelProperty("售后单信息")
	private RefundInfoDTO refundInfoDTO;

	@ApiModelProperty("物流信息")
	private RefundLogisticsDTO refundLogisticsDTO;

	@ApiModelProperty(value = "商品")
	private List<AfterSaleItem> items;
	/**
	 * 第三方收货人信息
	 */
	@ApiModelProperty(value = "第三方收货人信息")
	private AfterSaleReceiveInfoDTO afterSaleReceiveInfoDTO;

}
