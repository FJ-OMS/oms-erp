package org.skyer.order.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.api.vo.AfterSaleItemLineVO;
import org.skyer.order.domain.entity.AfterSaleItem;
import org.skyer.order.domain.entity.AfterSaleItemLine;
import org.skyer.order.domain.entity.ItemLine;

import java.util.List;

/**
 * 售后单商品行资源库
 *
 * @author lzh 2022-03-16 10:55:40
 */
public interface AfterSaleItemLineRepository extends BaseRepository<AfterSaleItemLine> {

    /**
     * 根据itemId获取可退款的商品行
     * @param itemId itemId
     * @return List<AfterSaleItemLineVO>
     */
    List<AfterSaleItemLineVO> returnLisByItemId(Long itemId);

    /**
     * 根据itemIds获取可退款的itemLine
     * @param itemIds itemIds
     * @return List<ItemLine>
     */
    List<AfterSaleItemLine> returnListByItemIds(List<Long> itemIds);

    /**
     * 根据itemIds获取可退货的itemLine
     * @param itemIds itemIds
     * @return List<ItemLine>
     */
    List<AfterSaleItemLine> returnGoodsListByItemIds(List<Long> itemIds);

    /**
     * 根据itemId获取可退货的商品行
     * @param itemId itemId
     * @return List<AfterSaleItemLineVO>
     */
    List<AfterSaleItemLineVO> returnGoodsListByItemId(Long itemId);


    /**
     * 通过inner查询
     * @param innerRefundNo
     * @return
     */
    List<AfterSaleItemLine>  queryByInnerRefundOrderNo(String innerRefundNo);

    /**
     * 通过innerRefundNo和originItemId展开商品行
     * @param afterSaleItemLine afterSaleItemLine
     * @return List<AfterSaleItemLine>
     */
    List<AfterSaleItemLine> itemLineDetails(AfterSaleItemLine afterSaleItemLine);


}
