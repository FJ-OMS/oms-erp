package org.skyer.order.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.skyer.order.api.dto.AfterSaleItemLineDTO;
import org.skyer.order.api.vo.AfterSaleItemLineVO;
import org.skyer.order.domain.entity.AfterSaleItemLine;
import org.skyer.mybatis.common.BaseMapper;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.entity.ItemLine;

import java.util.List;

/**
 * 售后单商品行Mapper
 *
 * @author lzh 2022-03-16 10:55:40
 */
public interface AfterSaleItemLineMapper extends BaseMapper<AfterSaleItemLine> {
    /**
     *  获取可退款的itemLine
     * @param itemId itemId
     * @return List<AfterSaleItemLineVO>
     */
    List<AfterSaleItemLineVO>  getReturnListByItemId(@Param("itemId")Long itemId);

    /**
     * 根据itemIds获取可退款的itemLine
     * @param itemIds itemIds
     * @return List<ItemLine>
     */
    List<AfterSaleItemLine> getReturnListByItemIds(@Param("itemIds") List<Long> itemIds);


    /**
     *  获取可退货的itemLine
     * @param itemId itemId
     * @return List<AfterSaleItemLineVO>
     */
    List<AfterSaleItemLineVO>  getReturnGoodsListByItemId(@Param("itemId")Long itemId);

    /**
     * 根据itemIds获取可退货的itemLine
     * @param itemIds itemIds
     * @return List<ItemLine>
     */
    List<AfterSaleItemLine> getReturnGoodsListByItemIds(@Param("itemIds") List<Long> itemIds);

}
