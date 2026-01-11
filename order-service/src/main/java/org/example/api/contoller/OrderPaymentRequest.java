package org.example.api.contoller;

import org.example.api.http.payment.PaymentMethod;

public record OrderPaymentRequest (
        PaymentMethod paymentMethod
) {
}
