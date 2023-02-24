package org.skyer.order.statemachine;

/**
 * 订单状态迁移事件
 *
 * @author chenzz
 */
public interface OrderStateEvent {
    /**
     * 订单状态事件
     *
     * @return
     */
    OrderEventsEnum getEventType();

    /**
     * 订单ID
     *
     * @return
     */
    String getOrderId();

    /**
     * 如果orderState不为空，则代表只有订单是当前状态才进行迁移
     *
     * @return
     */
    default OrderEventsEnum orderState() {
        return null;
    }

}