package com.smart_shop.CartService.api_response.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart_shop.CartService.enums.CartStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartCreateDTO {

    @NotBlank(message = "User ID cannot be blank")
    private String userId;
    @NotEmpty(message = "Cart must contain at least one item")
    private List<CartItemCreateDTO> cartItemCreateDTOs;
}
