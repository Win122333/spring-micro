package org.example.api.http.payment;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record CreatePaymentRequestDto (
        Long customerId,
        BigDecimal amount,
        PaymentMethod method
){
}
