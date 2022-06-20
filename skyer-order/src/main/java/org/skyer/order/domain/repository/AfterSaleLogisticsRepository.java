package org.skyer.order.domain.repository;

import java.util.List;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.domain.entity.AfterSaleLogistics;

/**
 * 资源库
 *
 * @author lzh 2022-03-18 16:26:17
 */
public interface AfterSaleLogisticsRepository extends BaseRepository<AfterSaleLogistics> {

	/**
	 * 通过内部退款单号查询物流
	 * @param inner
	 * @return
	 */
	List<AfterSaleLogistics> queryByInnerRefundNo(String inner);
}
