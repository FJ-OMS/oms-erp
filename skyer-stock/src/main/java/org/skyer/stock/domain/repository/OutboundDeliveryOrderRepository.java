package org.skyer.stock.domain.repository;

import org.skyer.core.domain.Page;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.stock.api.dto.OutboundDeliveryOrderPageDTO;
import org.skyer.stock.domain.entity.OutboundDeliveryOrder;
import org.skyer.stock.domain.vo.OutboundDeliveryOrderListVO;

/**
 * 出库单资源库
 *
 * @author lixiaoyang 2021-10-15 15:24:26
 */
public interface OutboundDeliveryOrderRepository extends BaseRepository<OutboundDeliveryOrder> {

    /**
     * 分页查询列表
     * @param dto 查询参数
     * @param pageRequest 分页参数
     * @return 带分页的列表数据
     */
    Page<OutboundDeliveryOrderListVO> page(OutboundDeliveryOrderPageDTO dto, PageRequest pageRequest);
}
