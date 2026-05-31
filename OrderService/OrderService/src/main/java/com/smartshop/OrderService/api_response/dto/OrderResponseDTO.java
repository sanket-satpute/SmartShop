package com.smartshop.OrderService.api_response.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartshop.OrderService.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderResponseDTO {

    private String id;
    private String userId;
    private List<OrderItemResponseDTO> orderItemResponseDTO;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private double totalAmount;
}
