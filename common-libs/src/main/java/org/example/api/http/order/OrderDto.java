package org.example.api.http.order;


import java.math.BigDecimal;
import java.util.Set;

public record OrderDto (
        Long id,
        OrderStatus status,
        String address,
        Integer etaMinutes,
        BigDecimal totalAmount,
        String courierName,
        Set<OrderItemDto> items
){

}
