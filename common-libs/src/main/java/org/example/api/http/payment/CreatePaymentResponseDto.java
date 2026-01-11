package org.example.api.http.payment;



import java.math.BigDecimal;

public record CreatePaymentResponseDto (
        Long paymentId,
        Long oderId,
        BigDecimal amount,
        PaymentMethod method,
        PaymentStatus status
){
}
