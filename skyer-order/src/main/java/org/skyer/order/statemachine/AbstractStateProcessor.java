package org.skyer.order.statemachine;

import org.springframework.stereotype.Component;

/**
 * 状态机处理器模板类
 */
@Component
public abstract class AbstractStateProcessor implements StateActionStep {

    /**
     * 初始状态
     */
    protected OrderStatusEnum initState;

    /**
     * 事件执行后状态
     */
    protected OrderStatusEnum nextState;

    @Override
    public ServiceResult action(StateContext context) throws Exception {
        ServiceResult result = null;
        try {
            // 数据准备
            this.prepare(context);
            // 串行校验器
            result = this.check(context);
            if (!result.isSuccess()) {
                return result;
            }
            // 业务逻辑
            result = this.action(context);
            if (!result.isSuccess()) {
                return result;
            }
            // 持久化
            result = this.save(nextState, context);
            if (!result.isSuccess()) {
                return result;
            }
            // after
            this.after(context);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
}