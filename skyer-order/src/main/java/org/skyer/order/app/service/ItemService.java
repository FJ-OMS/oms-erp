package org.skyer.order.app.service;

import org.skyer.order.api.dto.OrderItemDTO;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.infra.feign.dto.SearchOrderDTO;
import org.skyer.order.infra.feign.vo.OrderSkuVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 内部订单商品明细应用服务
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
public interface ItemService {
    /**
     * 对订单明细进行价格均摊
     * @param
     */
    List<Item> splitAmountByOrderItem(List<Item> itemList, BigDecimal totalPrice);

    /**
     * 订单详情的价格均摊
     * @param innerOrderNo
     */
    void detailPriceShare(String innerOrderNo);

    /**
     * 写回订单明细
     * @param
     */
    void createItem(List<OrderItemDTO> orderItemDTOList,String innerOrderNo);

    /**
     * 创建商品明细后回写到三方单
     * @param orderItemDTOList orderItemDTOList
     * @param innerOrderNo innerOrderNo
     */
    void thirdCreateItem(List<OrderItemDTO> orderItemDTOList,String innerOrderNo);
}
