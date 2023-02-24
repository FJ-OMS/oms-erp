package org.skyer.stock.domain.repository;

import java.util.List;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.stock.domain.entity.OutboundDeliveryOrderDetail;
import org.skyer.stock.domain.vo.OdoQtyVO;

/**
 * 出库商品详情资源库
 *
 * @author lixiaoyang 2021-10-15 15:24:25
 */
public interface OutboundDeliveryOrderDetailRepository extends BaseRepository<OutboundDeliveryOrderDetail> {

    List<OutboundDeliveryOrderDetail> getByOdoId(Long odoId);

    /**
     * 统计出库通知计划发货总数和已发货总数
     * @param odoId 出库通知单id
     * @return OdoQtyVO
     */
    OdoQtyVO statsOdoQty(Long odoId);
}
