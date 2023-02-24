package org.skyer.order.app.statemachine;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.skyer.order.config.States;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine
@Data
@Slf4j
public class BizBean {

    /**
     * @see States
     */
    private String status = States.INIT.name();

    // @OnTransition(source = {States.PUBLISH_TODO.toString()}, target = "WAIT_DELIVER")
    // public boolean payTransition(Message<Events> message) {
    //     System.out.println("----------------------------");
    //     message.getPayload()
    //     Order order = (Order) message.getHeaders().get("order");
    //     order.setStatus(OrderStatus.WAIT_DELIVER);
    //     System.out.println("支付 headers=" + message.getHeaders().toString() + " event=" + message.getPayload());
    //     System.out.println("----------------------------");
    //     return true;
    // }


    @OnTransition(target = "CREATE_INNER_ORDER_OK")
    public void create_ok() {
        log.info("target status:{}", States.CREATE_INNER_ORDER_OK.name());
        setStatus(States.CREATE_INNER_ORDER_OK.name());
    }

    @OnTransition(target = "YU_ZHAN_OK")
    public void yuZhan() {
        log.info(" target status:{}", States.YU_ZHAN_OK.name());
        setStatus(States.YU_ZHAN_OK.name());
    }

    @OnTransition(target = "XUN_YUAN_OK")
    public void xunYuan() {
        log.info("target status:{}", States.XUN_YUAN_OK.name());
        setStatus(States.XUN_YUAN_OK.name());
    }

}
