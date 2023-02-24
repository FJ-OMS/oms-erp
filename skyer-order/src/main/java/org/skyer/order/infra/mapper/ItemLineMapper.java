package org.skyer.order.infra.mapper;

import org.apache.ibatis.annotations.Param;

import org.skyer.order.api.vo.*;
import org.skyer.order.domain.entity.ItemLine;
import org.skyer.mybatis.common.BaseMapper;

/**
 * 内部订单商品行Mapper
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
public interface ItemLineMapper extends BaseMapper<ItemLine> {


    OrderStatusTotalVO totalCurrentLineStatus(String innerOrderNo);

    UnfinishedTotalVO totalUnfinishedLineStatus(String innerOrderNo);

    WaitDealTotalVO totalWaitDealLineStatus(String innerOrderNo);

	/**
	 * 计算缺货
	 * @param storeId
	 * @param skuCode
	 * @return
	 */
	Integer countStockout(@Param("storeId") Long storeId,@Param("skuCode") String skuCode);
}
