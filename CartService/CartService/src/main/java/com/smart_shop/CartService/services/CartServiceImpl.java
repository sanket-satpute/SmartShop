package com.smart_shop.CartService.services;

import com.smart_shop.CartService.api_response.dto.CartCreateDTO;
import com.smart_shop.CartService.api_response.dto.CartItemResponseDTO;
import com.smart_shop.CartService.api_response.dto.CartResponseDTO;
import com.smart_shop.CartService.entity.Cart;
import com.smart_shop.CartService.enums.CartStatus;
import com.smart_shop.CartService.exceptions.CartNotFoundException;
import com.smart_shop.CartService.exceptions.UserNotExistException;
import com.smart_shop.CartService.http_web_feign_calls.HttpProductService;
import com.smart_shop.CartService.http_web_feign_calls.HttpUserService;
import com.smart_shop.CartService.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final HttpUserService userService;

    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService, HttpUserService userService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public CartResponseDTO addToCart(CartCreateDTO cartCreateDTO) {
        if(!userService.isUserExistWithThisId(cartCreateDTO.getUserId())){
            throw new UserNotExistException("User not found with id: " + cartCreateDTO.getUserId());
        }
        String uuid = "C-" + UUID.randomUUID();
        List<CartItemResponseDTO> cartItemResponseDTOS = cartItemService.createCartItems(uuid, cartCreateDTO.getCartItemCreateDTOs());
        Cart cart = new Cart(uuid, cartCreateDTO.getUserId(), CartStatus.ACTIVE, LocalDateTime.now());
        cartRepository.save(cart);
        return CartResponseDTO.builder()
                .id(cart.getId())
                .status(cart.getStatus())
                .cartItemResponseDTO(cartItemResponseDTOS)
                .timestamps(cart.getTimestamps())
                .build();
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void clearCart(String userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        cartItemService.removeAllCartItemsByCartId(carts.stream().map(Cart::getId).toList());
        cartRepository.deleteByUserId(userId);
    }

    @Override
    public List<CartResponseDTO> getCartByUserId(String userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        return carts.stream().map(cart -> {
            List<CartItemResponseDTO> cartItemResponses = cartItemService.getCartItemsByCartId(cart.getId());
            return CartResponseDTO.builder()
                    .id(cart.getId())
                    .status(cart.getStatus())
                    .cartItemResponseDTO(cartItemResponses)
                    .timestamps(cart.getTimestamps())
                    .build();
        }).toList();
    }

    @Override
    public CartResponseDTO getCartId(String cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));
        List<CartItemResponseDTO> cartItemResponses = cartItemService.getCartItemsByCartId(cartId);
        return CartResponseDTO.builder()
                .id(cart.getId())
                .status(cart.getStatus())
                .cartItemResponseDTO(cartItemResponses)
                .timestamps(cart.getTimestamps())
                .build();
    }

    @Override
    public List<CartResponseDTO> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream().map(cart -> {
            List<CartItemResponseDTO> cartItemResponses = cartItemService.getCartItemsByCartId(cart.getId());
            return CartResponseDTO.builder()
                    .id(cart.getId())
                    .status(cart.getStatus())
                    .cartItemResponseDTO(cartItemResponses)
                    .timestamps(cart.getTimestamps())
                    .build();
        }).toList();
    }
}
