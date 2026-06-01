package com.SmartShop.Consumer;


import com.SmartShop.Consumer.entity.Notification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void receiveNotification(Notification notification) {
        // Logic to process received notification
        System.out.println("Received notification: " + notification.getMessage());
    }
}
