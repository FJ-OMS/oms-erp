package org.skyer.stock.domain.service;

import org.skyer.core.domain.Page;
import org.skyer.stock.api.dto.OutboundDeliveryOrderDTO;
import org.skyer.stock.api.dto.OutboundDeliveryOrderInfoDTO;
import org.skyer.stock.api.dto.OutboundDeliveryOrderPageDTO;
import org.skyer.stock.domain.vo.OutboundDeliveryOrderInfoVO;
import org.skyer.stock.domain.vo.OutboundDeliveryOrderListVO;

/**
 * 出库单应用服务
 *
 * @author lixiaoyang 2021-10-15 15:24:26
 */
public interface OutboundDeliveryOrderService {


    /**
     * 生成出库单
     *
     * @author lixiaoyang
     * @date 2021/10/15 17:07
     */
    OutboundDeliveryOrderDTO generateOutboundDeliveryOrder(OutboundDeliveryOrderInfoDTO dto);

    /**
     * 分页查询
     * @param dto 列表查询对象
     * @return 分页列表
     */
    Page<OutboundDeliveryOrderListVO> getPage(OutboundDeliveryOrderPageDTO dto);

    /**
     * 查询出库通知单详情
     * @param id 出库单id
     * @return OutboundDeliveryOrderInfoVO
     */
    OutboundDeliveryOrderInfoVO detail(Long id);
}
