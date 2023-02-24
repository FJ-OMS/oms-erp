package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import org.skyer.order.domain.entity.AfterSaleItem;
import org.skyer.order.domain.entity.AfterSaleItemLine;

/**
 * @author: lzh
 * @date: 2022-03-16
 * @description:
 */
@Data
public class RefundDetailsVO {

	@ApiModelProperty(value = "退款基础信息")
	private RefundBaseInfoVO refundBaseInfoVO;

	@ApiModelProperty(value = "买家信息")
	private RefundBuyerInfoVO refundBuyerInfoVO;

	@ApiModelProperty(value = "物流信息")
	private RefundLogisticsInfoVO refundLogisticsInfoVO;

	@ApiModelProperty(value = "商品")
	private List<AfterSaleItem> items;
}
