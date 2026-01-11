package org.example.domain.entity;

import org.example.api.http.payment.CreatePaymentRequestDto;
import org.example.api.http.payment.CreatePaymentResponseDto;
import org.example.domain.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentEntity responseToEntity(CreatePaymentResponseDto responseDto);
    PaymentEntity requestToEntity(CreatePaymentRequestDto requestDto);
    @Mapping(source = "id", target = "paymentId")
    CreatePaymentResponseDto entityToResponse(PaymentEntity entity);
}
