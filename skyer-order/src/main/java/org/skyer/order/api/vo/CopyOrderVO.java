package org.skyer.order.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.skyer.order.api.dto.OrderBaseInfoDTO;
import org.skyer.order.api.dto.OrderBuyerInfoDTO;
import org.skyer.order.api.dto.OrderItemDTO;
import org.skyer.order.api.dto.OrderPaymentInfoDTO;
import org.skyer.starter.keyencrypt.core.Encrypt;

/**
 * @author: lzh
 * @date: 2022-02-21
 * @description:
 */
@Data
public class CopyOrderVO {

	@ApiModelProperty("订单基础信息")
	private OrderBaseInfoDTO orderBaseInfoDTO;

	@ApiModelProperty("订单买家信息")
	private OrderBuyerInfoDTO orderBuyerInfoDTO;

	@ApiModelProperty("订单支付信息")
	private OrderPaymentInfoDTO orderPaymentInfoDTO;

	@ApiModelProperty("订单商品详情")
	private List<CopyOrderItemVO> itemVOList;
}
