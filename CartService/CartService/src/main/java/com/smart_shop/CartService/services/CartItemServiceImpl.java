package com.smart_shop.CartService.services;

import com.smart_shop.CartService.api_response.dto.CartItemCreateDTO;
import com.smart_shop.CartService.api_response.dto.CartItemResponseDTO;
import com.smart_shop.CartService.api_response.dto.ProductResponseDTO;
import com.smart_shop.CartService.entity.CartItem;
import com.smart_shop.CartService.exceptions.CartNotFoundException;
import com.smart_shop.CartService.http_web_feign_calls.HttpProductService;
import com.smart_shop.CartService.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final HttpProductService productService;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, HttpProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    @Override
    public List<CartItemResponseDTO> createCartItems(String cartId, List<CartItemCreateDTO> cartItemCreateDTOs) {
        List<CartItemResponseDTO> cartItemsResponse = cartItemCreateDTOs.stream().map(dto -> {
            String uuid = "CI-" + UUID.randomUUID();
            ProductResponseDTO productResponseDTO = productService.getProductById(dto.getProductId());
            return CartItemResponseDTO
                    .builder()
                    .id(uuid)
                    .cartId(cartId)
                    .productResponseDTO(productResponseDTO)
                    .quantity(dto.getQuantity())
                    .unitPriceSnapshot(productResponseDTO.getPrice())
                    .totalPriceSnapshot(productResponseDTO.getPrice() * dto.getQuantity())
                    .build();
        }).toList();
        List<CartItem> cartItemsToSave = cartItemsResponse.stream().map(response -> {
            CartItem cartItem = new CartItem();
            cartItem.setId(response.getId());
            cartItem.setCartId(response.getCartId());
            cartItem.setProductId(response.getProductResponseDTO().getId());
            cartItem.setQuantity(response.getQuantity());
            cartItem.setUnitPriceSnapshot(response.getUnitPriceSnapshot());
            cartItem.setTotalPriceSnapshot(response.getTotalPriceSnapshot());
            return cartItem;
        }).toList();
        cartItemRepository.saveAll(cartItemsToSave);
        return cartItemsResponse;
    }

    @Override
    public List<CartItemResponseDTO> getCartItemsByCartId(String cartId) {
        return cartItemRepository.findByCartId(cartId).stream().map(cartItem -> {
            ProductResponseDTO productResponseDTO = productService.getProductById(cartItem.getProductId());
            return CartItemResponseDTO
                    .builder()
                    .id(cartItem.getId())
                    .cartId(cartItem.getCartId())
                    .productResponseDTO(productResponseDTO)
                    .quantity(cartItem.getQuantity())
                    .unitPriceSnapshot(cartItem.getUnitPriceSnapshot())
                    .totalPriceSnapshot(cartItem.getTotalPriceSnapshot())
                    .build();
        }).toList();
    }

    @Override
    public void removeAllCartItemsByCartId(List<String> cartIds) {
        cartItemRepository.deleteByCartIdIn(cartIds);
    }

    @Override
    public void removeCartItemById(String cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public CartItemResponseDTO updateCartItemQuantity(String cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new CartNotFoundException("Cart item with id " + cartItemId + " not found"));
        cartItem.setQuantity(quantity);
        cartItem.setTotalPriceSnapshot(cartItem.getUnitPriceSnapshot() * quantity);
        cartItemRepository.save(cartItem);
        return CartItemResponseDTO
                .builder()
                .id(cartItem.getId())
                .cartId(cartItem.getCartId())
                .productResponseDTO(productService.getProductById(cartItem.getProductId()))
                .quantity(cartItem.getQuantity())
                .unitPriceSnapshot(cartItem.getUnitPriceSnapshot())
                .totalPriceSnapshot(cartItem.getTotalPriceSnapshot())
                .build();
    }
}
