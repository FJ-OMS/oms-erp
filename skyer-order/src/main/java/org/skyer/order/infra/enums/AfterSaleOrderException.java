package org.skyer.order.infra.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: lzh
 * @date: 2022-04-24
 * @description: 售后单异常枚举
 */
@AllArgsConstructor
@Getter
public enum AfterSaleOrderException {

	/**
	 * 正常
	 */
	OUT_ORDER_NORMAL("0", "正常"),

	/**
	 * 无关联第三方单
	 */
	NO_SUCH_OUT_ORDER_EXCEPTION("1", "无关联第三方单"),
	/**
	 * 渠道信息异常
	 */
	CHANNEL_INFO_EXCEPTION("2", "渠道信息异常"),
	/**
	 * 店铺信息异常
	 */
	STORE_INFO_EXCEPTION("3", "店铺信息异常"),
	/**
	 * SKU不符异常
	 */
	SKU_NOT_MATCH_EXCEPTION("4", "SKU不符异常"),
	/**
	 * SKU数量超出可退数量异常
	 */
	SKU_NUM_OVER_EXCEPTION("5", "SKU数量超出可退数量异常"),
	/**
	 * SKU退款金额超出可退金额异常
	 */
	SKU_AMOUNT_OVER_EXCEPTION("6", "SKU退款金额超出可退金额异常"),
	/**
	 * 退货地址信息异常
	 */
	RETURN_ADDRESS_EXCEPTION("7", "退货地址信息异常"),

	/**
	 * 三方单流转状态异常
	 */
	OUT_ORDER_STATUS_EXCEPTION("8", "三方单流转状态异常"),
	/**
	 * 其他异常
	 */
	OTHER_EXCEPTION("9", "其他异常");

	private final String code;
	private final String message;

}
