package org.example.domain.entity;

import org.example.api.contoller.OrderPaymentRequest;
import org.example.api.http.order.CreateOrderRequestDto;
import org.example.api.http.order.OrderDto;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface OrderMapper {

    OrderEntity toEntity(CreateOrderRequestDto requestDto);

    @AfterMapping
    default void linkOrderItemEntities(@MappingTarget OrderEntity orderEntity) {
        orderEntity
                .getItems()
                .forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
    }

    OrderDto toDto(OrderEntity orderEntity);
    OrderDto requestToDto(OrderPaymentRequest request);
}
