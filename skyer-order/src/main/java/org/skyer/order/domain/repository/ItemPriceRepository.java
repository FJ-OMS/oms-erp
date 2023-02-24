package org.skyer.order.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.api.vo.PriceTypeListVO;
import org.skyer.order.domain.entity.ItemPrice;

import java.util.List;
import java.util.Map;

/**
 * 内部订单明细价格（聚合）资源库
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
public interface ItemPriceRepository extends BaseRepository<ItemPrice> {

    /**
     * 根据商品id获取价格
     * @param itemId
     * @return
     */
    Map<String, ItemPrice> queryItemPrice(Long itemId);


	/**
	 * 获取商品项价格
	 * @param idList
	 * @return
	 */
	Map<Long, Map<String, ItemPrice>> queryItemPriceMap(List<Long> idList);

	/**
	 * 获取价格类型
	 * @return
	 */
	List<PriceTypeListVO>  getPriceList();
}
