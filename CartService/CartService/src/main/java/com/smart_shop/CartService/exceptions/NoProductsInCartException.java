package com.smart_shop.CartService.exceptions;

public class NoProductsInCartException extends RuntimeException {
    public NoProductsInCartException(String message) {
        super(message);
    }
}
