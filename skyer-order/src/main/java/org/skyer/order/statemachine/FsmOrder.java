package org.skyer.order.statemachine;

/**
 * 状态机引擎所需的订单信息基类信息
 *
 * @author chenzz
 */
public interface FsmOrder {
    /**
     * 订单编号
     */
    String getOrderSn();

    /**
     * 订单状态
     */
    OrderStatusEnum getOrderState();

}