package dev.cheng.spring.controller;

import dev.cheng.spring.core.Events;
import dev.cheng.spring.core.Order;
import dev.cheng.spring.core.SendEventDto;
import dev.cheng.spring.core.States;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private final StateMachineFactory<States, Events> stateMachineFactory;

    private final Map<Integer, StateMachine<States, Events>> orderStateMachines = new HashMap<>();

    private final Map<Integer, Order> orders = new HashMap<>();

    private final AtomicInteger orderId = new AtomicInteger();

    @PostMapping("/create")
    public ResponseEntity<Order> create() {
        int newOrderId = orderId.getAndIncrement();
        log.info("Generate newOrderId: {}", newOrderId);
        StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine(Integer.toString(newOrderId));
        orderStateMachines.put(newOrderId, stateMachine);

        Order order = Order.builder().id(newOrderId).state(stateMachine.getState().getId()).build();
        orders.put(newOrderId, order);
        return ResponseEntity.ok(order);
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> list = orders.values().stream().toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getByOrderId(@PathVariable("orderId") Integer orderId) {

        return ResponseEntity.ok(orders.get(orderId));
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<Order> sendEvent(@PathVariable("orderId") Integer orderId, @RequestBody SendEventDto sendEventDto) {
        if (!orders.containsKey(orderId))
            throw new IllegalArgumentException("Can not find order by orderId: " + orderId);

        StateMachine<States, Events> stateMachine = orderStateMachines.get(orderId);
        stateMachine.sendEvent(sendEventDto.getEvent());

        Order order = orders.get(orderId);
        order.setState(stateMachine.getState().getId());
        orders.put(orderId, order);

        return ResponseEntity.ok(order);
    }
}
