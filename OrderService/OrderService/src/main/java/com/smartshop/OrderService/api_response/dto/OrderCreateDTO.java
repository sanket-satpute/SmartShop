package com.smartshop.OrderService.api_response.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderCreateDTO {
    private String userId;
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemCreateDTO> orderItem;
}
