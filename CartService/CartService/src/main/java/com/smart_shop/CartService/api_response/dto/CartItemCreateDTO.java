package com.smart_shop.CartService.api_response.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemCreateDTO {

    @NotBlank(message = "Product ID cannot be blank")
    private String productId;
    @Positive(message = "Quantity must be greater than zero")
    private int quantity;
}
