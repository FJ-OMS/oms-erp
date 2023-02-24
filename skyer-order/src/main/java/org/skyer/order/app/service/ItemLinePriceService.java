package org.skyer.order.app.service;

import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.entity.ItemLine;

import java.util.List;

/**
 * 内部订单商品行价格应用服务
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
public interface ItemLinePriceService {
    /**
     * 维护商品价格
     * @param itemLineList
     */
    void saveItemLinePrice(List<ItemLine> itemLineList);
}
