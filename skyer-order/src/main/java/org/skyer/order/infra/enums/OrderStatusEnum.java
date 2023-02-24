package org.skyer.order.infra.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description
 * @Author wusc
 * @create 2022/2/9 2:36 下午
 */
@AllArgsConstructor
@Getter
public enum OrderStatusEnum {
    /**
     * 初始化正常
     */
    INIT_NORMAL(30000),
    /**
     * 异常转正
     */
    INIT_RETURN_NORMAL(30010),
    /**
     * 重置
     */
    INIT_RESET(30020),
    /**
     * 添加商品
     */
    INIT_ADD_ITEM(30030),
    /**
     * 待预占
     */
    PREEMPTION_WAIT(30101),
    /**
     * 预占成功
     */
    PREEMPTION_SUCCESS(30102),
    /**
     * 预占失败
     */
    PREEMPTION_FAIL(30103),
    /**
     * 部分预占成功
     */
    PART_PREEMPTION_SUCCESS(30104),

    /**
     * 待预占回写
     */
    PREEMPTION_WRITE_WAIT(30111),
    /**
     * 预占回写成功
     */
    PREEMPTION_WRITE_SUCCESS(30112),
    /**
     * 预占回写失败
     */
    PREEMPTION_WRITE_FAIL(30113),
    /**
     * 预占后一审待一审
     */
    PREEMPTION_AUDIT_ONE_WAIT(30211),
    /**
     * 预占后一审成功
     */
    PREEMPTION_AUDIT_ONE_SUCCESS(30212),
    /**
     * 预占后一审失败
     */
    PREEMPTION_AUDIT_ONE_FAIL(30213),
    /**
     * 预占后待二审
     */
    PREEMPTION_AUDIT_TWO_WAIT(30221),
    /**
     * 预占后待二审成功
     */
    PREEMPTION_AUDIT_TWO_SUCCESS(30222),
    /**
     * 预占后待二审失败
     */
    PREEMPTION_AUDIT_TWO_FAIL(30223),
    /**
     * 待寻源
     */
    SOURCING_WAIT(30301),
    /**
     * 寻源成功
     */
    SOURCING_SUCCESS(30302),
    /**
     * 寻源失败
     */
    SOURCING_FAIL(30303),
    /**
     * 寻源部分成功
     */
    SOURCING_PART_SUCCESS(30304),

    /**
     * 取消寻源
     */
    SOURCING_CANCEL(30305),
    /**
     * 寻源待一审
     */
    SOURCING_ONE_WAIT(30411),
    /**
     * 寻源一审成功
     */
    SOURCING_ONE_SUCCESS(30412),
    /**
     * 寻源一审失败
     */
    SOURCING_ONE_FAIL(30413),
    /**
     * 寻源待二审
     */
    SOURCING_TWO_WAIT(30421),
    /**
     * 寻源二审成功
     */
    SOURCING_TWO_SUCCESS(30422),
    /**
     * 寻源二审失败
     */
    SOURCING_TWO_FAIL(30423),
    /**
     * 发货初始化
     */
    SEND_INIT(30510),
    /**
     * 待发货
     */
    SEND_WAIT(30511),
    /**
     * 发货成功
     */
    SEND_SUCCESS(30512),
    /**
     * 发货失败
     */
    SEND_FAIL(30513),
    /**
     * 部分发货成功
     */
    SEND_PART_SUCCESS(30514),
    /**
     * 待收货
     */
    RECEIVE_WAIT(30521),
    /**
     * 收货成功
     */
    RECEIVE_SUCCESS(30522),
    /**
     * 部分收货成功
     */
    PART_RECEIVE_SUCCESS(30523),
    /**
     * 收货待回写
     */
    SEND_WRITE_WAIT(30531),
    /**
     * 收货回写成功
     */
    SEND_WRITE_SUCCESS(30532),
    /**
     * 收货回写失败
     */
    SEND_WRITE_FAIL(30533),
    /**
     * 正常履约完成
     */
    COMPLETE(31000),
    /**
     * 商品行取消
     */
    CANCEL(31010),
    /**
     * 商品sku不匹配
     */
    EXCEPTION_SKU_INFO(31111),
    /**
     * 系统挂起正常
     */
    SUSPEND_SYSTEM_NORMAL(31210),
    /**
     * 系统挂起成功
     */
    SUSPEND_SYSTEM_SUCCESS(31211),
    /**
     * 系统挂起失败
     */
    SUSPEND_SYSTEM_FAIL(31212),
    /**
     * 系统解挂成功
     */
    UN_SUSPEND_SYSTEM_SUCCESS(31213),
    /**
     * 系统解挂失败
     */
    UN_SUSPEND_SYSTEM_FAIL(31214),
    /**
     * 人工挂起正常
     */
    SUSPEND_ARTIFICIAL_NORMAL(31210),
    /**
     * 全部挂起成功
     */
    SUSPEND_ARTIFICIAL_SUCCESS(31201),
    /**
     * 全部挂起失败
     */
    SUSPEND_ARTIFICIAL_FAIL(31202),
    /**
     * 未挂起
     */
    UN_SUSPEND_ARTIFICIAL_SUCCESS(31200),
    /**
     * 部分挂起成功
     */
    PART_SUSPEND_SUCCESS(31203),
    /**
     * 人工解挂失败
     */
    UN_SUSPEND_ARTIFICIAL_FAIL(31204);

    Integer code;

}
