package com.smart_shop.CartService.http_web_feign_calls;

import com.smart_shop.CartService.api_response.dto.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE")
public interface HttpProductService {

    // update the product from the product service it is subtracting the product quantity from the product service and adding it to the cart service
    @PostMapping("/products/cart/{id}?howMuch={howMuch}")
    ProductResponseDTO addToCartProduct(@PathVariable String id, @RequestParam int howMuch);

    @GetMapping("/products/{id}")
    ProductResponseDTO getProductById(@PathVariable String id);

}
