package org.skyer.order.statemachine;

/**
 * 事件处理接口
 */
public interface EventDoAction {
    /**
     * 准备数据
     */
    default void prepare(StateContext context) {
    }

    /**
     * 校验
     */
    ServiceResult check();

    /**
     * 状态动作方法，主要状态迁移逻辑
     */
    ServiceResult action() throws Exception;

    /**
     * 状态数据持久化
     */
    ServiceResult save() throws Exception;

    /**
     * 状态迁移成功，持久化后执行的后续处理
     */
    void after();
}