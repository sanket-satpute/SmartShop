package com.SmartShop.NotificationService.controller;

import com.SmartShop.NotificationService.entity.Notification;
import com.SmartShop.NotificationService.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

//    @Autowired
//    NotificationService service;
//
//
//    @PostMapping
//    public ResponseEntity<?> sendNotification(@RequestBody Notification notification) {
//        // Logic to send notification
//        service.sendNotification(notification);
//        return ResponseEntity.ok("Notification sent successfully");
//    }

}
