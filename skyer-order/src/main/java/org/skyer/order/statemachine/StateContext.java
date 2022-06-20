package org.skyer.order.statemachine;

public class StateContext {

    private OrderExtraData orderExtraData;

    public StateContext(OrderExtraData orderExtraData) {
        this.orderExtraData = orderExtraData;
    }

    public OrderExtraData getOrderExtraData() {
        return orderExtraData;
    }

    public void setOrderExtraData(OrderExtraData orderExtraData) {
        this.orderExtraData = orderExtraData;
    }
}
