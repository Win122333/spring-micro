package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.http.payment.CreatePaymentRequestDto;
import org.example.api.http.payment.CreatePaymentResponseDto;
import org.example.api.http.payment.PaymentMethod;
import org.example.domain.PaymentRepository;
import org.example.domain.entity.PaymentEntity;
import org.example.api.http.payment.PaymentStatus;
import org.example.domain.entity.PaymentMapper;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    public CreatePaymentResponseDto makePayment(CreatePaymentRequestDto request) {
        var found = repository.findByOrderId(request.customerId());
        if (found.isPresent()) {
            log.info("Payment already exists for orderId={}", request.customerId());
            return mapper.entityToResponse(found.get());
        }

        var entity = mapper.requestToEntity(request);

        var status = request.method().equals(PaymentMethod.QR)
                ? PaymentStatus.PAID_FAIL
                : PaymentStatus.PAID_SUCCESS;

        entity.setStatus(status);

        var savedEntity = repository.save(entity);
        return mapper.entityToResponse(savedEntity);
    }
}
