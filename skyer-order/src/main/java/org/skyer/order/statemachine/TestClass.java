package org.skyer.order.statemachine;

import org.skyer.order.statemachine.service.PreemptionAction;

public class TestClass {
    public static void main(String[] args) throws Exception {
        StateContext context = new StateContext(new OrderExtraData("", null));
        PreemptionAction preemptionAction = new PreemptionAction(context, OrderStatusEnum.CREATE_ORDER_OK);

        ServiceResult result = preemptionAction.action();

        System.out.println(result.getMessage());
        System.out.println(preemptionAction.getNextStatus());
    }
}
