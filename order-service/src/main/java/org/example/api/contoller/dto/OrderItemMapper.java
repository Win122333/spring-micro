package org.example.api.contoller.dto;

import org.example.domain.entity.OrderItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItemEntity entity);
    OrderItemEntity toEntity(OrderItemRequestDto dto);
}
