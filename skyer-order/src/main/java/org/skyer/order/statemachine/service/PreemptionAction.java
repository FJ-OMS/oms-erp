package org.skyer.order.statemachine.service;

import cn.hutool.core.util.StrUtil;
import org.skyer.order.statemachine.AbstractEventProcessor;
import org.skyer.order.statemachine.OrderStatusEnum;
import org.skyer.order.statemachine.ServiceResult;
import org.skyer.order.statemachine.StateContext;

public class PreemptionAction extends AbstractEventProcessor {

    public PreemptionAction(StateContext context, OrderStatusEnum initState) {
        super(context, initState);
    }

    @Override
    public ServiceResult check() {
        String orderSn = getContext().getOrderExtraData().getOrderSn();
        if (StrUtil.isEmpty(orderSn)) {
            // return new ServiceResult(false, "订单号不能为空!");
            throw new RuntimeException("订单号不能为空!");
        }
        return new ServiceResult(true, "ok.");
    }

    @Override
    public ServiceResult action() throws Exception {
        check();
        if (getOriginalStatus() == OrderStatusEnum.CREATE_ORDER_OK) {
            if (getContext().getOrderExtraData().getOrderSn().equals("10000")) {
                setNextStatus(OrderStatusEnum.PREEMPTION_OK);
                return new ServiceResult(true, "预占成功, 订单状态已更新");
            }
        }
        return new ServiceResult(false, "预占失败!");
    }
}
