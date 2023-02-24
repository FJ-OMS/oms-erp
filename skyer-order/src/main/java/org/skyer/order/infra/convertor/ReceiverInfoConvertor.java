package org.skyer.order.infra.convertor;

import org.mapstruct.Mapper;
import org.skyer.order.api.dto.ReceiverInfoDTO;
import org.skyer.order.api.vo.OrderTotalVO;
import org.skyer.order.api.vo.ReceiverInfoVO;
import org.skyer.order.domain.entity.ReceiverInfo;

/**
 * @description
 * @Author wusc
 * @create 2022/2/10 9:30 上午
 */
@Mapper(componentModel="spring")
public interface ReceiverInfoConvertor {

    ReceiverInfoVO receiverInfoToDto(ReceiverInfo receiverInfo);


    ReceiverInfo dtoToReceiverInfo(ReceiverInfoDTO receiverInfoDTO);

}
