package org.example.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.api.contoller.dto.CreateOrderRequestDto;
import org.example.api.contoller.dto.OrderMapper;
import org.example.domain.entity.OrderEntity;
import org.example.domain.entity.OrderItemEntity;
import org.example.domain.entity.OrderStatus;
import org.example.domain.repository.OrderRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

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
}
