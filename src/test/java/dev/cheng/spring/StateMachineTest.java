package dev.cheng.spring;

import dev.cheng.spring.core.Events;
import dev.cheng.spring.core.States;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

@SpringBootTest
public class StateMachineTest {

    @Autowired
    private StateMachineFactory<States, Events> stateMachineFactory;

    @Test
    void test1() {
        long orderId = 1L;
        StateMachine<States, Events> order1 = stateMachineFactory.getStateMachine(Long.toString(orderId));
        order1.start();
        Assertions.assertTrue(order1.sendEvent(Events.E1));
        Assertions.assertTrue(order1.sendEvent(Events.E2));
    }
}
