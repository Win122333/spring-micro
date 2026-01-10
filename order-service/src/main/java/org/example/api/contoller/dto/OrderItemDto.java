package org.example.api.contoller.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.example.domain.entity.OrderEntity;

import java.math.BigDecimal;

public record OrderItemDto (
        Long id,
        Long itemId,
        Integer quantity,
        BigDecimal priceAtPurchase
) {

}
