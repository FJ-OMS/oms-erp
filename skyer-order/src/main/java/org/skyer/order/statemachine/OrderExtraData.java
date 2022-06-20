package org.skyer.order.statemachine;

import java.util.Map;

/**
 * 订单等数据
 *
 * @author chenzz
 */
public class OrderExtraData {
    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 其他订单数据
     */
    Map<String, Object> extraData;

    public OrderExtraData(String orderSn, Map<String, Object> extraData) {
        this.orderSn = orderSn;
        this.extraData = extraData;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, Object> extraData) {
        this.extraData = extraData;
    }
}