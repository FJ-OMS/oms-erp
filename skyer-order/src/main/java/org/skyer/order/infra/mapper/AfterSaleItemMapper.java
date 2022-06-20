package org.skyer.order.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.skyer.order.api.dto.AfterSaleItemDTO;
import org.skyer.order.api.vo.AfterSaleItemVO;
import org.skyer.order.domain.entity.AfterSaleItem;
import org.skyer.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 售后单商品Mapper
 *
 * @author lzh 2022-03-16 10:55:40
 */
public interface AfterSaleItemMapper extends BaseMapper<AfterSaleItem> {

    /**
     * 获取可退款/退货商品
     * @param afterSaleItemDto afterSaleItemDto
     * @return List<AfterSaleItemVO>
     */
    List<AfterSaleItemVO> getReturnListByInnerNo(@Param("param") AfterSaleItemDTO afterSaleItemDto);

    /**
     * 根据ItemIds获取可退款的item信息
     * @param originItemIds originItemIds
     * @return List<AfterSaleItemVO>
     */
    List<AfterSaleItem> getReturnListByOriginItemIds(@Param("itemIds")List<Long> originItemIds);

    /**
     * 根据内部退款单号查询 AfterSaleItem
     * @param InnerRefundNo InnerRefundNo
     * @return  List<AfterSaleItem>
     */
    List<AfterSaleItem> getListByInnerRefundNo(@Param("InnerRefundNo")String InnerRefundNo);
}
