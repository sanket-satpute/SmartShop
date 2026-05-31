package com.smart_shop.CartService.services;

import com.smart_shop.CartService.api_response.dto.CartCreateDTO;
import com.smart_shop.CartService.api_response.dto.CartResponseDTO;

import java.util.List;

public interface CartService {
    CartResponseDTO addToCart(CartCreateDTO cartCreateDTO);
    void clearCart(String userId);
    CartResponseDTO getCartId(String cartId);
    List<CartResponseDTO> getCartByUserId(String userId);
    List<CartResponseDTO> getAllCarts();
}
