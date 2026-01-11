package org.example.domain.entity;

import org.example.api.contoller.OrderPaymentRequest;
import org.example.api.http.order.CreateOrderRequestDto;
import org.example.api.http.order.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface OrderMapper {
    OrderDto toDto(OrderEntity entity);
    OrderEntity toEntity(CreateOrderRequestDto dto);

    OrderDto requestToDto(OrderPaymentRequest request);
}
