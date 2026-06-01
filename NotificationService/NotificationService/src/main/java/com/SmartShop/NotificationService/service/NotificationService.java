package com.SmartShop.NotificationService.service;

import com.SmartShop.NotificationService.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    KafkaTemplate<String, Notification> kafkaTemplate;

    public String sendNotification(Notification notification) {
        kafkaTemplate.send("notification-topic", notification);
        return "Notification sent successfully";
    }
}
