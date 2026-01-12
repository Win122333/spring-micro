package org.example.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.kafka.DeliveryAssignedEvent;
import org.example.domain.service.OrderService;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
@Slf4j
@EnableKafka
@Configuration
@RequiredArgsConstructor
public class OrderConsumer {
    private final OrderService orderService;

    @KafkaListener(
            topics = "${delivery-assigned-topic}",
            containerFactory = "orderPaidEventListenerFactory"
    )
    public void listen(DeliveryAssignedEvent event) {
        log.info("получили из кафки");
        orderService.updateAssignDelivery(event);
    }
}
