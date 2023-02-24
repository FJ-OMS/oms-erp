package org.skyer.order.statemachine;

/**
 * 状态迁移动作处理步骤
 */
public interface StateActionStep {
    /**
     * 准备数据
     */
    default void prepare(StateContext context) {
    }

    /**
     * 校验
     */
    ServiceResult check(StateContext context);

    /**
     * 获取当前状态处理器处理完毕后，所处于的下一个状态
     */
    OrderStatusEnum getNextState(StateContext context);

    /**
     * 状态动作方法，主要状态迁移逻辑
     */
    ServiceResult action(StateContext context) throws Exception;

    /**
     * 状态数据持久化
     */
    ServiceResult save(OrderStatusEnum nextStatus, StateContext context) throws Exception;

    /**
     * 状态迁移成功，持久化后执行的后续处理
     */
    void after(StateContext context);
}