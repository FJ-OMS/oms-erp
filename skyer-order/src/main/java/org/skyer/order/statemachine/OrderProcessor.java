package org.skyer.order.statemachine;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 状态机引擎的处理器注解标识
 *
 * @author chenzz
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface OrderProcessor {
    /**
     * 指定状态，state不能同时存在
     */
    OrderEventsEnum state() default OrderEventsEnum.CREATE_ORDER;


    /**
     * 订单操作事件
     */
    OrderEventsEnum event() default OrderEventsEnum.CREATE_ORDER;
}
