package org.skyer.order.app.dto;

import lombok.Data;
import org.skyer.order.api.dto.OrderItemDTO;
import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.entity.Item;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2022/1/19 9:44 上午
 */
@Data
public class OrderLineWorkDTO {
    /**
     * 订单头
     */
    private Header header;

    /**
     * 订单商品
     */
    private List<Item> item;

    /**
     * 订单商品dto列表
     */
    List<OrderItemDTO> orderItemDTOList;
}
