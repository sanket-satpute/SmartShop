package com.smart_shop.CartService.controller;

import com.smart_shop.CartService.api_response.dto.CartItemResponseDTO;
import com.smart_shop.CartService.services.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart/item")
public class CartItemController {

    private final CartItemService service;

    public CartItemController(CartItemService service) {
        this.service = service;
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItemById(@PathVariable String cartItemId) {
        service.removeCartItemById(cartItemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItemResponseDTO> updateCartItemQuantity(@PathVariable String cartItemId, @RequestParam int quantity) {
        return ResponseEntity.ok(service.updateCartItemQuantity(cartItemId, quantity));
    }
}
