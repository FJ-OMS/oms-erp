package org.skyer.order.config;

public enum States {
    INIT,

    /**
     *
     */
    CREATE_INNER_ORDER_OK,

    /**
     * 预占
     */
    YU_ZHAN_OK,

    /**
     * 寻源
     */
    XUN_YUAN_OK,

    /**
     * 发货通知
     */
    SEND_GOOD_OK,

    /**
     * 审核
     */
    VERIFY_OK,
}
