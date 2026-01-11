package org.example.kafka;

import lombok.RequiredArgsConstructor;
import org.example.api.kafka.DeliveryAssignedEvent;
import org.example.api.kafka.OrderPaidEvent;
import org.example.domain.DeliveryEntity;
import org.example.domain.DeliveryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final KafkaTemplate<Long, DeliveryAssignedEvent> template;
    @Value("${delivery-assigned-topic}")
    private String topic;
    public DeliveryEntity save(OrderPaidEvent event) {
        var orderId = event.orderId();
        var found = deliveryRepository.findByOrderId(orderId);
        if (found.isPresent()) {
            throw new RuntimeException("orderId already exists");
        }
        var entity = new DeliveryEntity();
        entity.setOrderId(orderId);
        entity.setCourierName("courier-" + ThreadLocalRandom.current().nextInt(100));
        entity.setEtaMinutes(ThreadLocalRandom.current().nextInt(40, 200));
        return deliveryRepository.save(entity);
    }
    private void sendDeliveryEvent(DeliveryEntity entity) {
        template.send(topic,
                DeliveryAssignedEvent.builder()
                        .courierName(entity.getCourierName())
                        .etaMinutes(entity.getEtaMinutes())
                        .orderId(entity.getOrderId())
                .build());
    }
}
