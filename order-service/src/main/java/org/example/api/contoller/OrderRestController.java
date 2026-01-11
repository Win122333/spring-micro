package org.example.api.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.http.order.CreateOrderRequestDto;
import org.example.api.http.order.OrderDto;
import org.example.domain.entity.OrderMapper;
import org.example.domain.service.OrderService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public OrderDto create(
            @RequestBody CreateOrderRequestDto request
    ) {
        log.info("Creating order: request={}", request);
        var saved = orderService.create(request);
        return orderMapper.toDto(saved);
    }
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAll() {
        log.info("called getAll");
        var found = orderService.getAll().stream()
                .map(orderMapper::toDto)
                .toList();
        return ResponseEntity.ok(found);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(
            @PathVariable Long id
    ) throws ChangeSetPersister.NotFoundException {
        log.info("called getById with id == {}", id);
        var found = orderService.getById(id);
        return ResponseEntity.ok(orderMapper.toDto(found));
    }
    @PostMapping("/{id}/pay")
    @Transactional
    public ResponseEntity<OrderDto> save(
            @PathVariable Long id,
            @RequestBody OrderPaymentRequest request
            ) throws ChangeSetPersister.NotFoundException {
        log.info("called save");
        var create = orderService.processPayment(id, request);
        return ResponseEntity.ok(orderMapper.toDto(create));
    }
}
