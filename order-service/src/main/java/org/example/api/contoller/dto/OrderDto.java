package org.example.api.contoller.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.example.domain.entity.OrderItemEntity;
import org.example.domain.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.Set;

public record OrderDto (
        Long id,
        OrderStatus status,
        String address,
        Integer etaMinutes,
        BigDecimal totalAmount,
        String courierName,
        Set<OrderItemEntity> items
){

}
