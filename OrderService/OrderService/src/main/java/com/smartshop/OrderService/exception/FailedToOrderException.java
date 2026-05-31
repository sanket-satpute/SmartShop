package com.smartshop.OrderService.exception;

public class FailedToOrderException extends RuntimeException {
    public FailedToOrderException(String message) {
        super(message);
    }
}
