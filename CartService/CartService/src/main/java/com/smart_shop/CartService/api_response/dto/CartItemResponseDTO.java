package com.smart_shop.CartService.api_response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponseDTO {

    private String id;
    private String cartId;
    private ProductResponseDTO productResponseDTO;
    private int quantity;
    private double unitPriceSnapshot;
    private double totalPriceSnapshot;
}
