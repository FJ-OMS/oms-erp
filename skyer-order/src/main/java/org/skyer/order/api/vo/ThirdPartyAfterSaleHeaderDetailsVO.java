package org.skyer.order.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.skyer.order.domain.entity.ThirdPartyAfterReceiverInfo;
import org.skyer.order.domain.entity.ThirdPartyAfterSaleHeader;
import org.skyer.order.domain.entity.ThirdPartyAfterSaleItem;
import org.skyer.order.domain.entity.ThirdPartyReturnLogistics;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-04-20
 * @description:
 */
@Data
public class ThirdPartyAfterSaleHeaderDetailsVO {

	/**
	 * 售后单头信息
	 */
	private ThirdPartyAfterSaleHeader thirdPartyAfterSaleHeader;

	/**
	 * 退货商品信息
	 */
	@ApiModelProperty(value = "退货商品信息")
	private List<ThirdPartyAfterSaleItem> thirdPartyAfterSaleDetailsVO;

	/**
	 * 退货物流信息
	 */
	@ApiModelProperty(value = "退货物流信息")
	private ThirdPartyReturnLogistics thirdPartyAfterSaleLogistics;

	/**
	 * 退货人信息
	 */
	@ApiModelProperty(value = "退货物流信息")
	private ThirdPartyAfterReceiverInfo thirdPartyAfterReceiverInfo;

}
