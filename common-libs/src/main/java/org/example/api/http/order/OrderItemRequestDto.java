package org.example.api.http.order;

import java.math.BigDecimal;

public record OrderItemRequestDto(
        Long itemId,
        Integer quantity,
        String name
) {
}
