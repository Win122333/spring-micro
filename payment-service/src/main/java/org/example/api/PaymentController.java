package org.example.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.http.payment.CreatePaymentRequestDto;
import org.example.api.http.payment.CreatePaymentResponseDto;
import org.example.domain.entity.PaymentMapper;
import org.example.domain.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;
    @PostMapping
    public CreatePaymentResponseDto createPayment(
            @RequestBody CreatePaymentRequestDto requestDto
            ) {
        log.info("called createPayment with {}", requestDto);
        return paymentMapper.entityToResponse(
                paymentService.createPayment(paymentMapper.requestToEntity(requestDto)));

    }
}
