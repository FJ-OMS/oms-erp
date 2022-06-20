package org.skyer.order.domain.repository;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.order.api.dto.ItemSearchDTO;

import org.skyer.order.api.dto.ShopIdsAndSkuCodesDTO;
import org.skyer.order.api.vo.*;
import org.skyer.order.domain.entity.ItemLine;

import java.util.List;
import java.util.Map;

/**
 * 内部订单商品行资源库
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
public interface ItemLineRepository extends BaseRepository<ItemLine> {

    /**
     * 根据内部单号查询itemList(Map)
     * @param innerOrderList
     * @return
     */
    Map<String, List<ItemLine>> queryItemLineByInner(List<String> innerOrderList);

    /**
     * 查询本次审核单商品行
     * @param auditIdList
     * @return
     */
    Map<Long,List<ItemLine>> queryNormalItemLineByAuditId(List<Long> auditIdList);


    List<ItemLine> filterAbnormal(List<ItemLine> itemLines);
    /**
     *
     * @param itemLineIdList
     * @return
     */
    List<ItemLine> queryItemLineByItemLineId(String innerOrderNo,List<Long> itemLineIdList);

    /**
     * 根据内部单号查询itemList
     * @param innerOrderNo
     * @return
     */
    List<ItemLine> queryItemLineListByInner(String innerOrderNo);


    /**
     * 根据id批量查询
     * @param itemLineIds
     * @return
     */
    List<ItemLine> batchQueryItemLineByIds(List<Long> itemLineIds);

    /**
     * 获取平摊商品行列表
     * @param itemSearchDTO
     * @return
     */
    Page<OrderItemLinePageVO> queryItemLinePage(ItemSearchDTO itemSearchDTO);

    /**
     * 根据itemId获取平摊商品行列表
     * @param itemId
     * @return
     */
    List<OrderItemLinePageVO> queryItemLineList(Long itemId);

    /**
     * 根据商品id查询平摊商品行
     * @param itemId
     * @return
     */
    List<ItemLine> queryItemLineByItemId(Long itemId);

    /**
     * 查询预占状态的商品详情对应商品行信息
     * @param itemId
     * @param preemptionStatus
     * @return
     */
    List<ItemLine> queryItemLineByItemIdPreemStatus(Long itemId,String preemptionStatus);

    /**
     * 查询预占状态的订单no对应商品行信息
     * @param
     * @param preemptionStatus
     * @return
     */
    List<ItemLine> queryItemLineByOrderNoPreemStatus(String innerNo,String preemptionStatus);
    /**
     * 根据商品idList获取商品行idList
     * @param itemId
     * @return
     */
    List<ItemLine> queryItemLineByItemId(List<Long> itemId);

    /**
     * 查询商品行id对应商品行
     * @param itemLineId
     * @return
     */
    List<ItemLine> queryItemLineByIds(List<Long> itemLineId);

    /**
     * 根据包裹号查询商品行
     * @param packNo
     * @return
     */
    List<ItemLine> queryItemLineByPackNo(String packNo);

    /**
     *
     * @param sourceId
     * @return
     */
    List<ItemLine> queryItemLineBySourceId(Long sourceId);



    /**
     * 店铺缺货
     * @param shopIdsAndSkuCodesDTO
     * @return
     */
	List<ShopSkuStockoutVO> storeStockout(ShopIdsAndSkuCodesDTO shopIdsAndSkuCodesDTO);

    /**
     * 根据内部单号查询itemList
     * @param innerOrderList
     * @return
     */
    List<ItemLine> queryItemLineByInnerList(List<String> innerOrderList);

}
