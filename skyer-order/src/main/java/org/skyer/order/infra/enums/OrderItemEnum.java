package org.skyer.order.infra.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description
 * @Author wusc
 * @create 2022/2/11 2:16 下午
 */
@AllArgsConstructor
@Getter
public enum OrderItemEnum {
    /**
     * 初始化正常
     */
    INIT_NORMAL(20000),
    /**
     * 异常转正
     */
    INIT_RETURN_NORMAL(20010),
    /**
     * 重置
     */
    INIT_RESET(20020),
    /**
     * 添加商品
     */
    INIT_ADD_ITEM(20030),
    /**
     * 待预占
     */
    PREEMPTION_WAIT(20101),
    /**
     * 预占成功
     */
    PREEMPTION_SUCCESS(20102),
    /**
     * 预占失败
     */
    PREEMPTION_FAIL(20103),
    /**
     * 部分成功
     */
    PART_PREEMPTION_SUCCESS(20104),
    /**
     * 待预占会写
     */
    PREEMPTION_WRITE_WAIT(20111),
    /**
     * 预占回写成功
     */
    PREEMPTION_WRITE_SUCCESS(20112),
    /**
     * 预占回写失败
     */
    PREEMPTION_WRITE_FAIL(20113),
    /**
     * 预占后一审待一审
     */
    PREEMPTION_AUDIT_ONE_WAIT(20211),
    /**
     * 预占后一审成功
     */
    PREEMPTION_AUDIT_ONE_SUCCESS(20212),
    /**
     * 预占后一审失败
     */
    PREEMPTION_AUDIT_ONE_FAIL(20213),
    /**
     * 预占后待二审
     */
    PREEMPTION_AUDIT_TWO_WAIT(20221),
    /**
     * 预占后待二审成功
     */
    PREEMPTION_AUDIT_TWO_SUCCESS(20222),
    /**
     * 预占后待二审失败
     */
    PREEMPTION_AUDIT_TWO_FAIL(20223),
    /**
     * 待寻源
     */
    SOURCING_WAIT(20301),
    /**
     * 寻源成功
     */
    SOURCING_SUCCESS(20302),
    /**
     * 寻源失败
     */
    SOURCING_FAIL(20303),
    /**
     * 寻源部分成功
     */
    SOURCING_PART_SUCCESS(20304),
    /**
     * 寻源待一审
     */
    SOURCING_ONE_WAIT(20411),
    /**
     * 寻源一审成功
     */
    SOURCING_ONE_SUCCESS(20412),
    /**
     * 寻源一审失败
     */
    SOURCING_ONE_FAIL(20413),
    /**
     * 寻源待二审
     */
    SOURCING_TWO_WAIT(20421),
    /**
     * 寻源二审成功
     */
    SOURCING_TWO_SUCCESS(20422),
    /**
     * 寻源二审失败
     */
    SOURCING_TWO_FAIL(20423),
    /**
     * 寻源取消
     */
    SOURCING_CANCEL(20305),
    /**
     * 发货初始化
     */
    SEND_INIT(20510),
    /**
     * 待发货
     */
    SEND_WAIT(20511),
    /**
     * 发货成功
     */
    SEND_SUCCESS(20512),
    /**
     * 发货失败
     */
    SEND_FAIL(20513),
    /**
     * 部分发货成功
     */
    SEND_PART_SUCCESS(20514),
    /**
     * 收货初始
     */
    RECEIVE_INIT(20520),
    /**
     * 待收货
     */
    RECEIVE_WAIT(20521),
    /**
     * 收货成功
     */
    RECEIVE_SUCCESS(20522),
    /**
     * 部分收货成功
     */
    PART_RECEIVE_SUCCESS(20523),
    /**
     * 收货待回写
     */
    SEND_WRITE_WAIT(20531),
    /**
     * 收货回写成功
     */
    SEND_WRITE_SUCCESS(20532),
    /**
     * 收货回写失败
     */
    SEND_WRITE_FAIL(20533),
    /**
     * 正常履约完成
     */
    COMPLETE(21000),
    /**
     * 商品行取消
     */
    CANCEL(21010),
    /**
     * 系统挂起正常
     */
    SUSPEND_SYSTEM_NORMAL(21210),
    /**
     * 系统挂起成功
     */
    SUSPEND_SYSTEM_SUCCESS(21211),
    /**
     * 系统挂起失败
     */
    SUSPEND_SYSTEM_FAIL(21212),
    /**
     * 系统解挂成功
     */
    UN_SUSPEND_SYSTEM_SUCCESS(21213),
    /**
     * 系统解挂失败
     */
    UN_SUSPEND_SYSTEM_FAIL(21214),
    /**
     * 全部挂起成功
     */
    SUSPEND_ARTIFICIAL_SUCCESS(21201),
    /**
     * 部分挂起成功
     */
    PART_ARTIFICIAL_SUCCESS(21203),
    /**
     * 挂起失败
     */
    SUSPEND_ARTIFICIAL_FAIL(21202),
    /**
     * 未挂起
     */
    SUSPEND_ARTIFICIAL_NORMAL(21200),
    /**
     * 人工解挂成功
     */
    UN_SUSPEND_ARTIFICIAL_SUCCESS(21223),
    /**
     * 人工解挂失败
     */
    UN_SUSPEND_ARTIFICIAL_FAIL(21214);
    Integer code;
}
