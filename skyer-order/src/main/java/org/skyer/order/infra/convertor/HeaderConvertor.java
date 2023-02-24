package org.skyer.order.infra.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.skyer.boot.platform.lov.annotation.ProcessLovValue;
import org.skyer.order.api.dto.OrderBaseDTO;
import org.skyer.order.api.dto.OrderBaseInfoDTO;
import org.skyer.order.api.dto.OrderBuyerInfoDTO;
import org.skyer.order.api.dto.OrderPaymentInfoDTO;
import org.skyer.order.api.vo.OrderInfoVO;
import org.skyer.order.api.vo.OrderTotalVO;
import org.skyer.order.api.vo.PaymentInfoVO;
import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.entity.ReceiverInfo;
import org.skyer.order.infra.common.CommonConstants;

/**
 * @description
 * @Author wusc
 * @create 2022/1/18 4:45 下午
 */
@Mapper(componentModel="spring")
public interface HeaderConvertor {

    @Mappings({
            @Mapping(target = "cancelStatus",constant = CommonConstants.OrderCancelStatus.NO),
            @Mapping(target = "tradeType",constant = CommonConstants.OrderTradeType.SALE),
            @Mapping(target = "refundStatus",constant = CommonConstants.RefundStatus.NO),
            @Mapping(target = "returnStatus",constant = CommonConstants.ReturnStatus.NO),
            @Mapping(target = "exceptionFlag",constant = CommonConstants.ExceptionFlag.NORMAL),
            @Mapping(constant = CommonConstants.SuspendStatus.NORMAL,target = "suspendStatus"),
            @Mapping(constant = CommonConstants.EndorseStatus.NORMAL,target = "endorseStatus"),
            @Mapping(constant = CommonConstants.PreemptionStatus.PREEMPTION_WAIT,target = "preemptionStatus"),
            @Mapping(constant = CommonConstants.SourceStatus.SOURCING_WAIT,target = "sourceStatus"),
            @Mapping(constant = CommonConstants.SendStatus.SEND_INIT,target = "deliveryStatus"),
            @Mapping(constant = CommonConstants.SyncPreempStatus.PREEMPTION_WRITE_WAIT,target = "syncPreempStatus"),
            @Mapping(constant = CommonConstants.SyncSendStatus.SEND_INIT,target = "syncDeliveryStatus"),
            @Mapping(constant = CommonConstants.OrderAggrStatus.NO_COMPLETE,target = "orderStatus"),
            @Mapping(constant = CommonConstants.ReceivingStatus.RECEIVING_INIT,target = "receivingStatus")
    })
    Header dtoToHeader(OrderBaseInfoDTO orderBaseInfoDTO, OrderBuyerInfoDTO orderBuyerInfoDTO, OrderPaymentInfoDTO orderPaymentInfoDTO);


    ReceiverInfo dtoToReceiverInfo(OrderBuyerInfoDTO orderBuyerInfoDTO);


    OrderInfoVO headerToOrderInfoVO(Header header);

    PaymentInfoVO headerToPaymentVO(Header header);


    @Mappings({
            @Mapping(target = "id",source = "orderBaseDTO.orderId")
    })
    Header orderBaseToHeader(OrderBaseDTO orderBaseDTO);

}
