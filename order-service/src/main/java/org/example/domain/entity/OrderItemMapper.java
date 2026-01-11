package org.example.domain.entity;

import org.example.api.http.order.OrderItemDto;
import org.example.api.http.order.OrderItemRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItemEntity entity);
    OrderItemEntity toEntity(OrderItemRequestDto dto);
}
