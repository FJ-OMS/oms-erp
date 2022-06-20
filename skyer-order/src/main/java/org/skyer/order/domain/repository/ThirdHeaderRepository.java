package org.skyer.order.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.domain.entity.ThirdHeader;

/**
 * 第三方订单头资源库
 *
 * @author zhanglongbing@ebc.com 2022-04-20 10:15:56
 */
public interface ThirdHeaderRepository extends BaseRepository<ThirdHeader> {

	/**
	 * 根据三方订单号查询
	 *
	 * @param outOrderNo 订单号
	 * @return 第三方订单头
	 */
	ThirdHeader queryByOutOrderNo(String outOrderNo);
}
