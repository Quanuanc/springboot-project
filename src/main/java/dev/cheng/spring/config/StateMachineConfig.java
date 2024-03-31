package dev.cheng.spring.config;

import dev.cheng.spring.core.Events;
import dev.cheng.spring.core.States;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.EnumState;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Slf4j
@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                String fromStr;
                if (from == null) {
                    fromStr = States.SI.toString();
                } else {
                    fromStr = from.getId().name();
                }
                log.info("From {} change to {}", fromStr, to.getId());
            }
        };
    }


    @Override
    @SneakyThrows
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) {
        config.withConfiguration().autoStartup(true).listener(listener());
    }

    @Override
    @SneakyThrows
    public void configure(StateMachineStateConfigurer<States, Events> states) {
        states.withStates()
                .initial(States.SI)
                .states(EnumSet.allOf(States.class));
    }

    @SneakyThrows
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) {
        transitions.withExternal()
                .source(States.SI).target(States.S1).event(Events.E1)
                .and()
                .withExternal()
                .source(States.S1).target(States.S2).event(Events.E2);
    }
}
