package org.example.api.contoller.dto;

import org.example.api.contoller.dto.OrderDto;
import org.example.domain.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface OrderMapper {
    OrderDto toDto(OrderEntity entity);
    OrderEntity toEntity(CreateOrderRequestDto dto);
}
