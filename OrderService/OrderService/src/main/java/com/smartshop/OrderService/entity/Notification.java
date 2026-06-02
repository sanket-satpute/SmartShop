package com.smartshop.OrderService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private String userId;
    private String message;
    private String recipientEmail;
    private String subject;
    private LocalDateTime timestamp;
}
