package org.skyer.order.statemachine;

/**
 * 订单状态
 *
 * @author chenzz
 */
public enum OrderStatusEnum {
    //
    // CREATE_ORDER_OK(1, "创建订单成功"),
    // ORDER_CONFIRM_OK(2, "订单已确认"),
    // PREEMPTION_OK(3, "预占成功");

    CREATE_ORDER_OK(1, "订单创建成功"),
    // ORDER_CONFIRM(2, "订单确认(客服)"),
    PREEMPTION_OK(3, "预占成功"),
    // AUDIT_1(4, "第一次审核"),
    // AUDIT_2(5, "第二次审核"),
    SEARCH_SOURCE_OK(6, "寻源"),
    // AUDIT_1_AFTER_SS(4, "第一次审核(寻源成功后)"),
    // AUDIT_2_AFTER_SS(5, "第二次审核(寻源成功后)"),
    STOCK_DELIVERY_OK(6, "出库发货成功"),
    CONFIRM_RECEIVE_OK(7, "确认收货成功"),
    // 以下为退款及退货退款申请等逆向状态
    APPLY_REFUND_READY(8, "申请退款就绪(未审核)"),
    ORDER_REFUND_OK(9, "申请退货成功"),
    RETURN_GOODS(10, "返还货物");


    OrderStatusEnum(int code, String des) {
        this.code = code;
        this.des = des;
    }

    private final int code;
    private final String des;


    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
