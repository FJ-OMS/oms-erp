package org.skyer.order.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.domain.entity.AfterSaleReceiverInfo;

/**
 * 内部售后收货资源库
 *
 * @author linzhuanghuang@ebc.com 2022-04-26 17:19:33
 */
public interface AfterSaleReceiverInfoRepository extends BaseRepository<AfterSaleReceiverInfo> {

	/**
	 * 根据内部退款单号查询收货信息
	 *
	 * @param innerRefundNo
	 * @return
	 */
	AfterSaleReceiverInfo queryByInnerRefundOrderNo(String innerRefundNo);
}
