package com.smartshop.OrderService.api_response.dto;

import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderItemResponseDTO {
    private String id;
    private ProductResponseDTO productResponseDTO;
    private int quantity;
    private double price;
}
