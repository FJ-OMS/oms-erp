package org.skyer.stock.infra.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.stock.api.dto.OutboundDeliveryOrderPageDTO;
import org.skyer.stock.domain.entity.OutboundDeliveryOrder;
import org.skyer.stock.domain.repository.OutboundDeliveryOrderRepository;
import org.skyer.stock.domain.vo.OutboundDeliveryOrderListVO;
import org.skyer.stock.infra.mapper.OutboundDeliveryOrderMapper;

/**
 * 出库单 资源库实现
 *
 * @author lixiaoyang 2021-10-15 15:24:26
 */
@Component
public class OutboundDeliveryOrderRepositoryImpl extends BaseRepositoryImpl<OutboundDeliveryOrder> implements OutboundDeliveryOrderRepository {

    @Autowired
    private OutboundDeliveryOrderMapper outboundDeliveryOrderMapper;

    @Override
    public Page<OutboundDeliveryOrderListVO> page(OutboundDeliveryOrderPageDTO dto, PageRequest pageRequest) {

        return PageHelper.doPageAndSort(pageRequest,
                () -> outboundDeliveryOrderMapper.selectList(dto));

    }
}
