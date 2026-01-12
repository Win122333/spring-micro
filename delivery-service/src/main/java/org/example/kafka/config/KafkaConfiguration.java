package org.example.kafka.config;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.example.api.kafka.DeliveryAssignedEvent;
import org.example.api.kafka.OrderPaidEvent;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Bean
    DefaultKafkaProducerFactory<Long, DeliveryAssignedEvent> deliveryAssignedEventProducerFactory(
            KafkaProperties properties
    ) {
        Map<String, Object> producerProperties = properties.buildProducerProperties(null);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(producerProperties);
    }

    @Bean
    KafkaTemplate<Long, DeliveryAssignedEvent> deliveryAssignedEventKafkaTemplate(
            DefaultKafkaProducerFactory<Long, DeliveryAssignedEvent> deliveryAssignedEventProducerFactory
    ) {
        return new KafkaTemplate<>(deliveryAssignedEventProducerFactory);
    }

    @Bean
    public ConsumerFactory<Long, OrderPaidEvent> orderPaidEventConsumerFactory(
            KafkaProperties properties
    ) {
        Map<String, Object> props = properties.buildConsumerProperties(null);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "org.example");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, OrderPaidEvent.class.getName());
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, OrderPaidEvent>
    orderPaidEventListenerFactory(
            ConsumerFactory<Long, OrderPaidEvent> orderPaidEventConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<Long, OrderPaidEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderPaidEventConsumerFactory);
        factory.setBatchListener(false);
        return factory;
    }
}