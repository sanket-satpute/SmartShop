package com.smart_shop.CartService.controller;

import com.smart_shop.CartService.api_response.dto.CartCreateDTO;
import com.smart_shop.CartService.api_response.dto.CartResponseDTO;
import com.smart_shop.CartService.services.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CartResponseDTO> addToCart(@Valid @RequestBody CartCreateDTO cartCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addToCart(cartCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<CartResponseDTO>> getAllCarts() {
        return ResponseEntity.ok(service.getAllCarts());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartResponseDTO>> getCart(@PathVariable String userId) {
        return ResponseEntity.ok(service.getCartByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> getCartById(@PathVariable String id) {
        return ResponseEntity.ok(service.getCartId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> clearCart(@PathVariable String id) {
        service.clearCart(id);
        return ResponseEntity.noContent().build();
    }

}
