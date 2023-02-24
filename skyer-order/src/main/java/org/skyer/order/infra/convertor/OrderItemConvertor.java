package org.skyer.order.infra.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.skyer.order.api.dto.OrderItemDTO;
import org.skyer.order.api.vo.CopyOrderItemVO;
import org.skyer.order.api.vo.OrderItemLinePageVO;
import org.skyer.order.api.vo.OrderItemPageVO;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.entity.ItemSnapshot;
import org.skyer.order.infra.common.CommonConstants;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2022/1/18 4:57 下午
 */
@Mapper(componentModel="spring")
public interface OrderItemConvertor {

    @Mappings({
            @Mapping(source = "orderItemDTO.skuCode",target = "skuCode"),
            @Mapping(source = "itemSnapShotMap.skuId",target = "skuId"),
            @Mapping(constant = CommonConstants.EndorseStatus.NORMAL,target = "endorseStatus"),
            @Mapping(constant = CommonConstants.PreemptionStatus.ITEM_PREEMPTION_WAIT,target = "preemptionStatus"),
            @Mapping(constant = CommonConstants.SourceStatus.ITEM_SOURCING_WAIT,target = "sourceStatus"),
            @Mapping(constant = CommonConstants.SendStatus.ITEM_SEND_INIT,target = "deliveryStatus"),
            @Mapping(constant = CommonConstants.SyncPreempStatus.ITEM_PREEMPTION_WRITE_WAIT,target = "syncPreempStatus"),
            @Mapping(constant = CommonConstants.SyncSendStatus.ITEM_SEND_INIT,target = "syncDeliveryStatus"),
            @Mapping(constant = CommonConstants.ReceivingStatus.ITEM_RECEIVING_INIT,target = "receivingStatus"),
            @Mapping(constant = CommonConstants.ExceptionFlag.NORMAL,target = "exceptionFlag"),
            @Mapping(source = "orderItemDTO.giftFlag",target = "giftFlag"),
            @Mapping(source = "orderItemDTO.mainOrderItemCode",target = "mainItemCode")
    })
    Item dtoToOrderItem(OrderItemDTO orderItemDTO, ItemSnapshot itemSnapShotMap);


    List<OrderItemPageVO> itemTOPageVOList(List<Item> content);

    /**
     * 复制单转换
     * @param item
     * @return
     */
    CopyOrderItemVO dtoToCopyItem(Item item);
}
