package org.example.api.kafka;

import lombok.Builder;
import org.example.api.http.payment.PaymentMethod;

import java.math.BigDecimal;
@Builder
public record OrderPaidEvent(
    Long orderId,
    Long paymentId,
    BigDecimal amount,
    PaymentMethod method
) {}
