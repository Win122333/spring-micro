package org.example.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.kafka.OrderPaidEvent;
import org.example.domain.DeliveryEntity;
import org.example.domain.DeliveryRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@EnableKafka
@Configuration
@RequiredArgsConstructor
public class OrderPaidKafkaConsumer {
    private final DeliveryService deliveryService;
    @KafkaListener(
            topics = "${order-paid-topic}",
            containerFactory = "orderPaidEventListenerFactory"
    )
    public void listen(OrderPaidEvent event) {
        log.info("получили orderPaidEvent");

        deliveryService.save(event);
        log.info("success delivery");
    }
}
