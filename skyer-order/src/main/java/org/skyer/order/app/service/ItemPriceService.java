package org.skyer.order.app.service;

import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.entity.ItemPrice;

import java.util.List;
import java.util.Map;

/**
 * 内部订单明细价格（聚合）应用服务
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
public interface ItemPriceService {
    /**
     * 维护商品价格
     * @param itemList
     */
    void saveItemPrice(List<Item> itemList);

    /**
     * 维护价格均摊价
     * @param itemList
     */
    void saveBalanceItemPrice(List<Item> itemList);

    /**
     * 为item赋予价格
     * @param itemList
     */
    void setItemPrice(List<Item> itemList);
}
