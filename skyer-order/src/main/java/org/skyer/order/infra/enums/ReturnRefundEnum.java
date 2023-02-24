package org.skyer.order.infra.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: lzh
 * @date: 2022-03-22
 * @description:
 */
@AllArgsConstructor
@Getter
public enum ReturnRefundEnum {
	/**
	 * 仅退款一审
	 */
	REFUND_ONE_WAIT("31310"),
	/**
	 * 仅退款一审成功
	 */
	REFUND_ONE_SUCCESS("31311"),
	/**
	 * 仅退款一审失败
	 */
	REFUND_ONE_FAIL("31312"),
	/**
	 * 仅退款待二审
	 */
	REFUND_TWO_WAIT("31320"),
	/**
	 * 仅退款二审成功
	 */
	REFUND_TWO_SUCCESS("31321"),
	/**
	 * 仅退款二审失败
	 */
	REFUND_TWO_FAIL("31322"),
	/**
	 * 退货退款待一审
	 */
	REFUND_RETURN_ONE_WAIT("31410"),
	/**
	 * 退货退款一审成功
	 */
	REFUND_RETURN_SUCCESS("31411"),
	/**
	 * 退货退款一审失败
	 */
	REFUND_RETURN_ONE_FAIL("31412"),
	/**
	 * 退货退款待二审
	 */
	REFUND_RETURN_TWO_WAIT("31420"),
	/**
	 * 退货退款二审成功
	 */
	REFUND_RETURN_TWO_SUCCESS("31421"),
	/**
	 * 退货退款二审失败
	 */
	REFUND_RETURN_TWO_FAIL("31422");

	String code;
}
