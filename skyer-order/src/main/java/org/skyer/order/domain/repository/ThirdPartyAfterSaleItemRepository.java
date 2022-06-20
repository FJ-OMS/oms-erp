package org.skyer.order.domain.repository;

import java.util.List;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.domain.entity.ThirdPartyAfterSaleItem;

/**
 * 第三方售后单商品资源库
 *
 * @author lzh 2022-04-20 16:30:29
 */
public interface ThirdPartyAfterSaleItemRepository extends BaseRepository<ThirdPartyAfterSaleItem> {

	/**
	 * 根据三方订单号获取商品列表
	 * @param outRefundNo
	 * @return
	 */
	List<ThirdPartyAfterSaleItem> queryByOutRefundNo(String outRefundNo);
}
