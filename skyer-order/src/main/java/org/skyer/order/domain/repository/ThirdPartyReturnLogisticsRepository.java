package org.skyer.order.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.domain.entity.ThirdPartyReturnLogistics;

/**
 * 三方退货物流信息资源库
 *
 * @author lzh 2022-04-20 16:30:29
 */
public interface ThirdPartyReturnLogisticsRepository extends BaseRepository<ThirdPartyReturnLogistics> {

	/**
	 * 通过三方售后单号查询三方退货物流信息
	 * @param outerRefundNo
	 * @return
	 */
	ThirdPartyReturnLogistics queryByOuterRefundNo(String outerRefundNo);
}
