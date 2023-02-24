package org.skyer.order.statemachine;

/**
 * 订单事件
 *
 * @author
 */
public enum OrderEventsEnum {

    CREATE_ORDER(1, "创建订单"),
    ORDER_CONFIRM(2, "订单确认(客服)"),
    PREEMPTION(3, "预占"),
    AUDIT_1(4, "第一次审核"),
    AUDIT_2(5, "第二次审核"),
    SEARCH_SOURCE(6, "寻源"),
    AUDIT_1_AFTER_SS(4, "第一次审核(寻源成功后)"),
    AUDIT_2_AFTER_SS(5, "第二次审核(寻源成功后)"),
    STOCK_DELIVERY(6, "出库"),
    CONFIRM_RECEIVE(7, "确认收货"),
    // 以下为退款及退货退款申请等逆向事件
    APPLY_REFUND_MONEY(8, "申请退款"),
    APPLY_REFUND_MONEY_GOODS(9, "申请退货退款"),
    RETURN_GOODS(10, "返还货物"),
    AGREE_REFUND_MONEY(11, "同意退款"),
    RETURN_GOODS_IN_STOCK(12, "退货入库");

    OrderEventsEnum(int code, String des) {
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
