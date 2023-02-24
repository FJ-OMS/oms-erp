package org.skyer.stock.infra.mapper;


import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.skyer.mybatis.common.BaseMapper;
import org.skyer.stock.domain.entity.OutboundDeliveryOrderDetail;
import org.skyer.stock.domain.vo.OdoQtyVO;

/**
 * 出库商品详情Mapper
 *
 * @author lixiaoyang 2021-10-15 15:24:25
 */
public interface OutboundDeliveryOrderDetailMapper extends BaseMapper<OutboundDeliveryOrderDetail> {

    List<OutboundDeliveryOrderDetail> getByOdoId(@Param("odoId") Long odoId);

    /**
     * 统计出库通知单 发货数量
     * @param odoId 出库通知单id
     * @return OdoQtyVO
     */
    OdoQtyVO statsByOdoId(@Param("odoId") Long odoId);
}
