package org.skyer.stock.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.stock.domain.entity.OutboundDeliveryOrderDetail;
import org.skyer.stock.domain.repository.OutboundDeliveryOrderDetailRepository;
import org.skyer.stock.domain.vo.OdoQtyVO;
import org.skyer.stock.infra.mapper.OutboundDeliveryOrderDetailMapper;

/**
 * 出库商品详情 资源库实现
 *
 * @author lixiaoyang 2021-10-15 15:24:25
 */
@Component
public class OutboundDeliveryOrderDetailRepositoryImpl extends BaseRepositoryImpl<OutboundDeliveryOrderDetail> implements OutboundDeliveryOrderDetailRepository {
    @Autowired
    private OutboundDeliveryOrderDetailMapper outboundDeliveryOrderDetailMapper;
    @Override
    public List<OutboundDeliveryOrderDetail> getByOdoId(Long odoId) {
        return outboundDeliveryOrderDetailMapper.getByOdoId(odoId);
    }

    @Override
    public OdoQtyVO statsOdoQty(Long odoId) {
        return outboundDeliveryOrderDetailMapper.statsByOdoId(odoId);
    }
}
