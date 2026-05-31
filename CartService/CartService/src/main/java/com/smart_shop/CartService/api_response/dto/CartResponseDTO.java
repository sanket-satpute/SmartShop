package com.smart_shop.CartService.api_response.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart_shop.CartService.enums.CartStatus;
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
public class CartResponseDTO {

    private String id;
    private CartStatus status;
    private List<CartItemResponseDTO> cartItemResponseDTO;
    private LocalDateTime timestamps;
}
