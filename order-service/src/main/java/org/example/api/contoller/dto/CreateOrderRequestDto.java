package org.example.api.contoller.dto;

import org.example.domain.entity.OrderItemEntity;
import org.example.domain.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.Set;

public record CreateOrderRequestDto (
        Long customerId,
        String address,
        Set<OrderItemRequestDto> items
){
}
