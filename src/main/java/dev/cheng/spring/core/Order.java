package dev.cheng.spring.core;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    private Integer id;
    private States state;
}
