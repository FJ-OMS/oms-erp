package org.skyer.order.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.skyer.order.api.dto.SaleItemReportDTO;
import org.skyer.order.api.vo.SaleItemReportVO;
import org.skyer.order.domain.entity.Item;
import org.skyer.mybatis.common.BaseMapper;

import java.util.List;

/**
 * 内部订单商品明细Mapper
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
public interface ItemMapper extends BaseMapper<Item> {

    /**
     * 统计商品销售情况
     * @param saleItemReportDTO
     * @return
     */
    List<SaleItemReportVO> totalOrderItemSaleReport(@Param("dto") SaleItemReportDTO saleItemReportDTO);
}
