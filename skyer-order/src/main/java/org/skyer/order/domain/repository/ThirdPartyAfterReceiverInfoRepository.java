package org.skyer.order.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.domain.entity.ThirdPartyAfterReceiverInfo;

/**
 * 第三方售后收货人资源库
 *
 * @author lzh 2022-04-20 16:30:30
 */
public interface ThirdPartyAfterReceiverInfoRepository extends BaseRepository<ThirdPartyAfterReceiverInfo> {

	/**
	 * 根据三方订单号查询收货人信息
	 * @param outerRefundNo
	 * @return
	 */
	ThirdPartyAfterReceiverInfo queryByOuterRefundNo(String outerRefundNo);
}
