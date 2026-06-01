package com.SmartShop.Consumer.config;

import com.SmartShop.Consumer.entity.Notification;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Consumer Configuration
 *
 * CONCEPT:
 * - ConsumerFactory: factory that creates Kafka consumer instances
 * - ConcurrentKafkaListenerContainerFactory: Spring wrapper that manages
 *   the lifecycle of @KafkaListener methods
 * - JsonDeserializer: converts raw JSON bytes from Kafka into our Java object
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    /**
     * ConsumerFactory defines HOW to connect to Kafka and HOW to deserialize messages.
     *
     * Key settings:
     * - BOOTSTRAP_SERVERS_CONFIG: where Kafka broker is running
     * - GROUP_ID_CONFIG: consumer group name (all consumers in same group share load)
     * - AUTO_OFFSET_RESET_CONFIG: "earliest" = read from beginning if no offset saved
     * - JsonDeserializer(Notification.class, false):
     *     - first param: what Java class to deserialize into
     *     - second param (false): IGNORE __TypeId__ headers from producer
     *       This is what fixes the cross-service package mismatch issue!
     */
    @Bean
    public ConsumerFactory<String, Notification> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // false = do NOT use type headers, always deserialize as Notification.class
        JsonDeserializer<Notification> deserializer = new JsonDeserializer<>(Notification.class, false);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    /**
     * This factory is used by @KafkaListener.
     * Spring uses this bean automatically when it sees @KafkaListener in your code.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Notification> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Notification> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}

