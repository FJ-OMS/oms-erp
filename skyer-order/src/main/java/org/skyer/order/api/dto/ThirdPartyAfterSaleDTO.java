package org.skyer.order.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.skyer.order.domain.entity.ThirdPartyAfterSaleItem;
import org.skyer.order.domain.entity.ThirdPartyReturnLogistics;

/**
 * @author: lzh
 * @date: 2022-04-20
 * @description:
 */
@Data
public class ThirdPartyAfterSaleDTO {

	@ApiModelProperty(value = "售后基础信息")
	private ThirdPartyRefundBaseInfo thirdPartyRefundBaseInfo;
	/**
	 * 第三方退货商品信息
	 */
	@ApiModelProperty(value = "第三方退货商品信息")
	private List<ThirdPartyAfterSaleItem> thirdPartyAfterSaleGoodsList;

	/**
	 * 第三方退货物流信息
	 */
	@ApiModelProperty(value = "第三方退货物流信息")
	private ThirdPartyReturnLogistics thirdPartyAfterSaleLogisticsList;

	/**
	 * 第三方收货人信息
	 */
	@ApiModelProperty(value = "第三方收货人信息")
	private ThirdPartyReceiveInfoDTO thirdPartyReceiveInfo;
}


