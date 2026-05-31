package com.smart_shop.CartService.exceptions;

import com.smart_shop.CartService.api_response.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCartNotFoundException(CartNotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .massage(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(NoProductsInCartException.class)
    public ResponseEntity<ErrorResponse> handleNoProductInCartException(NoProductsInCartException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .massage(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<ErrorResponse> handleUserNotExistException(UserNotExistException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .massage(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(response, response.getStatus());
    }
}
