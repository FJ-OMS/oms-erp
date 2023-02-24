package org.skyer.stock.infra.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.skyer.mybatis.common.BaseMapper;
import org.skyer.stock.api.dto.OutboundDeliveryOrderPageDTO;
import org.skyer.stock.domain.entity.OutboundDeliveryOrder;
import org.skyer.stock.domain.vo.OutboundDeliveryOrderListVO;

/**
 * 出库单Mapper
 *
 * @author lixiaoyang 2021-10-15 15:24:26
 */
public interface OutboundDeliveryOrderMapper extends BaseMapper<OutboundDeliveryOrder> {

    /**
     * 根据订单ID获取
     * @author lixiaoyang
     * @date 2022/1/14 15:58
     */
    OutboundDeliveryOrder getByOrderId(@Param("orderId") Long orderId);

    /**
     * 查询出库通知单列表
     * @param dto 查询条件
     * @return OutboundDeliveryOrderListVO集合
     */
    List<OutboundDeliveryOrderListVO> selectList(OutboundDeliveryOrderPageDTO dto);
}
