package org.skyer.order.infra.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单行级别枚举
 * @description
 * @Author wusc
 * @create 2022/1/24 10:19 上午
 */
@AllArgsConstructor
@Getter
public enum OrderItemLineEnum {
    /**
     * 初始化正常
     */
    INIT_NORMAL(10000),
    /**
     * 异常转正
     */
    INIT_RETURN_NORMAL(10010),
    /**
     * 重置
     */
    INIT_RESET(10020),
    /**
     * 添加商品
     */
    INIT_ADD_ITEM(10030),
    /**
     * 待预占
     */
    PREEMPTION_WAIT(10101),
    /**
     * 预占成功
     */
    PREEMPTION_SUCCESS(10102),
    /**
     * 预占失败
     */
    PREEMPTION_FAIL(10103),
    /**
     * 待预占会写
     */
    PREEMPTION_WRITE_WAIT(10111),
    /**
     * 预占回写成功
     */
    PREEMPTION_WRITE_SUCCESS(10112),
    /**
     * 预占回写失败
     */
    PREEMPTION_WRITE_FAIL(10113),


    /**
     * 预占后一审待一审
     */
    PREEMPTION_AUDIT_ONE_WAIT(10211),
    /**
     * 预占后一审成功
     */
    PREEMPTION_AUDIT_ONE_SUCCESS(10212),
    /**
     * 预占后一审失败
     */
    PREEMPTION_AUDIT_ONE_FAIL(10213),
    /**
     * 预占后待二审
     */
    PREEMPTION_AUDIT_TWO_WAIT(10221),
    /**
     * 预占后待二审成功
     */
    PREEMPTION_AUDIT_TWO_SUCCESS(10222),
    /**
     * 预占后待二审失败
     */
    PREEMPTION_AUDIT_TWO_FAIL(10223),
    /**
     * 待寻源
     */
    SOURCING_WAIT(10301),
    /**
     * 寻源成功
     */
    SOURCING_SUCCESS(10302),
    /**
     * 寻源失败
     */
    SOURCING_FAIL(10303),
    /**
     * 寻源取消
     */
    SOURCING_CANCEL(10305),
    /**
     * 寻源待一审
     */
    SOURCING_ONE_WAIT(10411),
    /**
     * 寻源一审成功
     */
    SOURCING_ONE_SUCCESS(10412),
    /**
     * 寻源一审失败
     */
    SOURCING_ONE_FAIL(10413),
    /**
     * 寻源待二审
     */
    SOURCING_TWO_WAIT(10421),
    /**
     * 寻源二审成功
     */
    SOURCING_TWO_SUCCESS(10422),
    /**
     * 寻源二审失败
     */
    SOURCING_TWO_FAIL(10423),
    /**
     * 发货初始化
     */
    SEND_INIT(10510),
    /**
     * 待发货
     */
    SEND_WAIT(10511),
    /**
     * 发货成功
     */
    SEND_SUCCESS(10512),
    /**
     * 发货失败
     */
    SEND_FAIL(10513),
    /**
     * 待收货
     */
    RECEIVE_WAIT(10521),
    /**
     * 收货成功
     */
    RECEIVE_SUCCESS(10522),
    /**
     * 收货待回写
     */
    SEND_WRITE_WAIT(10531),
    /**
     * 收货回写成功
     */
    SEND_WRITE_SUCCESS(10532),
    /**
     * 收货回写失败
     */
    SEND_WRITE_FAIL(10533),
    /**
     * 正常履约完成
     */
    COMPLETE(11000),
    /**
     * 商品行取消
     */
    CANCEL(11010),
    /**
     * 系统挂起正常
     */
    SUSPEND_SYSTEM_NORMAL(11210),
    /**
     * 系统挂起成功
     */
    SUSPEND_SYSTEM_SUCCESS(11211),
    /**
     * 系统挂起失败
     */
    SUSPEND_SYSTEM_FAIL(11212),
    /**
     * 系统解挂成功
     */
    UN_SUSPEND_SYSTEM_SUCCESS(11213),
    /**
     * 系统解挂失败
     */
    UN_SUSPEND_SYSTEM_FAIL(11214),
    /**
     * 人工挂起正常
     */
    SUSPEND_ARTIFICIAL_NORMAL(11210),
    /**
     * 人工挂起成功
     */
    SUSPEND_ARTIFICIAL_SUCCESS(11221),
    /**
     * 人工挂起失败
     */
    SUSPEND_ARTIFICIAL_FAIL(11222),
    /**
     * 人工解挂成功
     */
    UN_SUSPEND_ARTIFICIAL_SUCCESS(11223),
    /**
     * 人工解挂失败
     */
    UN_SUSPEND_ARTIFICIAL_FAIL(11224);
    Integer code;
}
