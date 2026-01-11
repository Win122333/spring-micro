package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.http.payment.PaymentMethod;
import org.example.domain.PaymentRepository;
import org.example.domain.entity.PaymentEntity;
import org.example.api.http.payment.PaymentStatus;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    public PaymentEntity createPayment(PaymentEntity entity) {
        var found = paymentRepository.findByOrderId(entity.getOrderId());
        if (found.isPresent()) {
            log.info("payment already exits for orderId={}", entity.getOrderId());
            return entity;
        }

        if (entity.getMethod().compareTo(PaymentMethod.QR) == 0) {
            entity.setStatus(PaymentStatus.PAID_FAIL);
        }

        else {
            entity.setStatus(PaymentStatus.PAID_SUCCESS);
        }
        return paymentRepository.save(entity);
    }
}
