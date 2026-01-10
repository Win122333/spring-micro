package org.example.api.contoller.dto;

import java.math.BigDecimal;

public record OrderItemRequestDto(
        Long itemId,
        Integer quantity,
        String name
) {
}
