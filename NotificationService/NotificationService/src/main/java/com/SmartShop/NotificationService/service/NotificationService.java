package com.SmartShop.NotificationService.service;

import com.SmartShop.NotificationService.entity.Notification;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void receiveNotification(Notification notification) {
        // Logic to process received notification
        sendEmail(notification);
        System.out.println("Received notification: " + notification.getMessage());
    }

    public void sendEmail(Notification notification) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setTo(notification.getRecipientEmail());
            helper.setSubject(notification.getSubject());
            helper.setText(notification.getMessage(), true); // Set to true for HTML content
            helper.setFrom(senderEmail);
            mailSender.send(mimeMessage);
            System.out.println("HTML email sent successfully to " + notification.getRecipientEmail());
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}
