package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.api.http.order.CreateOrderRequestDto;
import org.example.api.http.payment.CreatePaymentRequestDto;
import org.example.api.http.payment.CreatePaymentResponseDto;
import org.example.api.http.payment.PaymentStatus;
import org.example.api.kafka.DeliveryAssignedEvent;
import org.example.api.kafka.OrderPaidEvent;
import org.example.domain.entity.OrderMapper;
import org.example.domain.entity.OrderEntity;
import org.example.domain.entity.OrderItemEntity;
import org.example.api.http.order.OrderStatus;
import org.example.api.contoller.OrderPaymentRequest;
import org.example.domain.repository.OrderRepository;
import org.example.external.PaymentHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Value("${order-paid-topic}")
    private String topic;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PaymentHttpClient paymentHttpClient;
    private final KafkaTemplate<Long, OrderPaidEvent> kafkaTemplate;

    public List<OrderEntity> getAll() {
        return orderRepository.findAll();
    }
    public OrderEntity getById(Long id) throws ChangeSetPersister.NotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }
    public OrderEntity create(CreateOrderRequestDto dto) {
        var entity = orderMapper.toEntity(dto);
        setStatusPendingPaid(entity);
        calculatePurchase(entity);
        calculateEtaMinutes(entity);

        return orderRepository.save(entity);
    }

    private void calculateEtaMinutes(OrderEntity entity) {
        entity.setEtaMinutes(ThreadLocalRandom.current().nextInt(20, 200));
    }

    private void setStatusPendingPaid(OrderEntity entity) {
        entity.setStatus(OrderStatus.PENDING_PAYMENT);
    }

    private void calculatePurchase(OrderEntity entity) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItemEntity it : entity.getItems()) {
            var randomPrice = ThreadLocalRandom.current().nextDouble(100, 1500);
            it.setPriceAtPurchase(BigDecimal.valueOf(randomPrice));

            totalPrice = it.getPriceAtPurchase()
                    .multiply(BigDecimal.valueOf(it.getQuantity()))
                    .add(totalPrice);
        }
        entity.setTotalAmount(totalPrice);
    }

    public OrderEntity processPayment(Long id, OrderPaymentRequest orderPaymentRequest)
            throws ChangeSetPersister.NotFoundException {
        var entity = getById(id);
        if (!entity.getStatus().equals(OrderStatus.PENDING_PAYMENT)) {
            throw new RuntimeException("order must be in PENDING_PAYMENT");
        }
        var response = paymentHttpClient.createPayment(
                CreatePaymentRequestDto.builder()
                        .customerId(entity.getCustomerId())
                        .method(orderPaymentRequest.paymentMethod())
                        .amount(entity.getTotalAmount())
                        .build());
        if (response.status().equals(PaymentStatus.PAID_SUCCESS)) {
            entity.setStatus(OrderStatus.PAID);
        }
        else {
            entity.setStatus(OrderStatus.PAYMENT_FAILED);
        }
        sendOrderPaidEvent(entity, response);
        return orderRepository.save(entity);
    }
    private void sendOrderPaidEvent(OrderEntity entity, CreatePaymentResponseDto responseDto) {
        kafkaTemplate.send(topic, entity.getId(), OrderPaidEvent.builder()
                        .orderId(entity.getId())
                        .amount(entity.getTotalAmount())
                        .method(responseDto.method())
                        .paymentId(responseDto.paymentId())
                .build());
    }
    public void updateAssignDelivery(DeliveryAssignedEvent event) {
        var found = orderRepository.findById(event.orderId()).orElseThrow(() -> new RuntimeException(""));
        found.setStatus(OrderStatus.DELIVERY_ASSIGNED);
        found.setCourierName(event.courierName());
        found.setEtaMinutes(event.etaMinutes());
        orderRepository.save(found);
    }
}
