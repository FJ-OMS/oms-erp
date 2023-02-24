package org.skyer.order.statemachine;

/**
 * 状态机事件处理器模板类
 */
public abstract class AbstractEventProcessor implements EventDoAction {

    private StateContext context;

    /**
     * 初始状态
     */
    private OrderStatusEnum originalStatus;

    /**
     * 事件执行后状态
     */
    private OrderStatusEnum nextStatus;

    protected AbstractEventProcessor(StateContext context, OrderStatusEnum initState) {
        this.context = context;
        this.originalStatus = initState;
    }

    /**
     * 获取执行后状态
     *
     * @return
     */
    public OrderStatusEnum getNextStatus() {
        if (nextStatus == null) {
            throw new RuntimeException("nextStatus is null");
        }
        return nextStatus;
    }

    /**
     * 获取原始状态
     *
     * @return
     */
    public OrderStatusEnum getOriginalStatus() {
        if (originalStatus == null) {
            throw new RuntimeException("originalStatus is null");
        }
        return originalStatus;
    }

    public void setNextStatus(OrderStatusEnum nextStatus) {
        if (nextStatus == originalStatus) {
            throw new RuntimeException("事件执行后状态不能和初始状态一样!");
        }
        this.nextStatus = nextStatus;
    }

    public StateContext getContext() {
        return context;
    }

    @Override
    public void prepare(StateContext context) {
        this.context = context;
    }

    @Override
    public ServiceResult save() throws Exception {
        return null;
    }

    @Override
    public void after() {

    }
}