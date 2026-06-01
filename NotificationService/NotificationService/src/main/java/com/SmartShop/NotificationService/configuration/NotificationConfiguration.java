package com.SmartShop.NotificationService.configuration;

import com.SmartShop.NotificationService.entity.Notification;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Producer Configuration
 *
 * CONCEPT:
 * - ProducerFactory: factory that creates Kafka producer instances
 * - KafkaTemplate: Spring helper that wraps the producer and provides
 *   easy methods like .send(topic, object)
 * - JsonSerializer: converts Java object → JSON bytes to store in Kafka
 */
@Configuration
public class NotificationConfiguration {

    /**
     * ProducerFactory defines HOW to connect to Kafka and HOW to serialize messages.
     *
     * Key settings:
     * - BOOTSTRAP_SERVERS_CONFIG: where Kafka broker is running
     * - JsonSerializer.ADD_TYPE_INFO_HEADERS → false:
     *     Do NOT add __TypeId__ header to messages.
     *     This prevents the cross-service class name mismatch problem.
     */
    @Bean
    public ProducerFactory<String, Notification> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        // Do NOT add type headers — consumer is in different service with different package
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * KafkaTemplate is what you @Autowire in NotificationService.
     * It wraps the producer and gives you .send(topic, value) method.
     */
    @Bean
    public KafkaTemplate<String, Notification> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
