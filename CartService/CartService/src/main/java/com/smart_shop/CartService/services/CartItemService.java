package com.smart_shop.CartService.services;

import com.smart_shop.CartService.api_response.dto.CartItemCreateDTO;
import com.smart_shop.CartService.api_response.dto.CartItemResponseDTO;

import java.util.List;

public interface CartItemService {

    public List<CartItemResponseDTO> createCartItems(String cartId, List<CartItemCreateDTO> cartItemCreateDTOs);

    public List<CartItemResponseDTO> getCartItemsByCartId(String cartId);

    public void removeAllCartItemsByCartId(List<String> cartIds);

    public void removeCartItemById(String cartItemId);

    public CartItemResponseDTO updateCartItemQuantity(String cartItemId, int quantity);
}
