package com.smartshop.OrderService.api_response.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderItemCreateDTO {
    @NotNull(message = "Product ID cannot be null")
    private String productId;
    @Positive(message = "Quantity must be greater than zero")
    private int quantity;
}
