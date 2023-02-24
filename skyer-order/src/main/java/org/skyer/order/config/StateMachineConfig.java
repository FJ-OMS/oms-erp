package org.skyer.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates().initial(States.INIT).states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions.withExternal()
                .source(States.INIT).target(States.CREATE_INNER_ORDER_OK)
                .event(Events.CREATE_INNER_ORDER)
                //--------------------
                .and()
                .withExternal()
                .source(States.CREATE_INNER_ORDER_OK).target(States.YU_ZHAN_OK)
                .event(Events.YU_ZHAN)
                //--------------------
                .and()
                .withExternal()
                .source(States.YU_ZHAN_OK).target(States.XUN_YUAN_OK)
                .event(Events.XUN_YUAN)
                //--------------------
                .and()
                .withExternal()
                .source(States.XUN_YUAN_OK).target(States.SEND_GOOD_OK)
                .event(Events.SEND_GOOD_NOTIFY);
    }
}
