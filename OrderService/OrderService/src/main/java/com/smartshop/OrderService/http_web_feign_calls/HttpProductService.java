package com.smartshop.OrderService.http_web_feign_calls;

import com.smartshop.OrderService.api_response.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE")
public interface HttpProductService {

    @PostMapping("/products/order/{id}?howMuch={howMuch}")
    ProductDTO orderProduct(@PathVariable String id, @RequestParam int howMuch);

    @GetMapping("/products/{id}")
    ProductDTO getProductById(@PathVariable String id);

}
