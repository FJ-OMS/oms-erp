package org.skyer.order.api.vo;

import lombok.Data;

/**
 * @author: lzh
 * @date: 2022-02-09
 * @description:
 */
@Data
public class OrderItemLineVO {
	/**
	 * 快递单号
	 */
	private String expressNo;

	/**
	 * 物流商
	 */
	private String logisticsCompany;

	/**
	 * 物流业务
	 */
	private String logisticsBusiness;
	/**
	 * SKU编码
	 */
	private String skuCode;
	/**
	 * SKU名称
	 */
	private String skuName;
	/**
	 * SPU编码
	 */
	private String spuCode;
	/**
	 * SPU名称
	 */
	private String spuName;

}
