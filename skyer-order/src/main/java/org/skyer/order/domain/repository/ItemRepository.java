package org.skyer.order.domain.repository;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.api.dto.*;
import org.skyer.order.api.vo.OrderItemPageVO;
import org.skyer.order.api.vo.SaleItemReportVO;
import org.skyer.order.domain.entity.Item;

import java.util.List;
import java.util.Map;

/**
 * 内部订单商品明细资源库
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
public interface ItemRepository extends BaseRepository<Item> {

    /**
     * 根据内部单号查询itemList
     * @param innerOrderList
     * @return
     */
    Map<String, List<Item>> queryItemByInner(List<String> innerOrderList);

    /**
     *
     * @param innerOrderNo
     * @return
     */
    List<Item> queryItemByInner(String innerOrderNo);

    /**
     * 获取订单商品列表
     * @param itemSearchDTO
     * @return
     */
    Page<OrderItemPageVO> queryItemPage(ItemSearchDTO itemSearchDTO);


	/**
	 * 根据ids获取itemlist
	 * @param itemIds
	 * @return
	 */
	List<Item> queryItemListByItemIds(List<Long> itemIds);

	/**
	 * 统计正向单商品销售情况
	 * @param saleItemReportDTO
	 * @return
	 */
	List<SaleItemReportVO> totalOrderItemSaleReport(SaleItemReportDTO saleItemReportDTO);
}
