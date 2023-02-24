package org.skyer.order.infra.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.skyer.order.api.dto.OrderItemDTO;
import org.skyer.order.app.dto.SplitItemAmountDTO;
import org.skyer.order.domain.entity.Item;

@Mapper(componentModel="spring")
public interface SplitIemAmountConvertor {

    @Mappings({
            @Mapping(source = "orderItemCode", target = "codeOrId"),
            @Mapping(source = "skuCode", target = "skuCode"),
            @Mapping(source = "quantity", target = "num"),
    })
    SplitItemAmountDTO orderItemDTOToSplitItemAmount(OrderItemDTO orderItemDTO);

    @Mappings({
            @Mapping(source = "orderItemCode", target = "codeOrId"),
            @Mapping(source = "skuCode", target = "skuCode"),
            @Mapping(source = "quantity", target = "num"),
    })
    SplitItemAmountDTO itemToSplitItemAmount(Item item);
    @Mappings({
        @Mapping(source = "item.skuCode",target = "skuCode"),
        @Mapping(source = "item.orderItemCode",target = "orderItemCode"),
        @Mapping(source = "splitItemAmountDTO.num",target = "quantity"),
        @Mapping(source = "splitItemAmountDTO.outBalancePrice",target = "outBalancePrice"),
        @Mapping(source = "splitItemAmountDTO.innerBalancePrice",target = "innerBalancePrice")
    })
    Item splitItemAmountDTOToOrderItemDTO(Item item,SplitItemAmountDTO splitItemAmountDTO);
}
