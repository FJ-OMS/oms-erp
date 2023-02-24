package org.skyer.order.statemachine;

public interface StateProcessor {
    /**
     * 执行状态迁移的入口
     */
    void action(StateContext context) throws Exception;
}
