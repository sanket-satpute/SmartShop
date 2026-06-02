package com.smartshop.OrderService.http_web_feign_calls;

import com.smartshop.OrderService.api_response.dto.ProductDTO;
import com.smartshop.OrderService.api_response.dto.UserResponseMailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE")
public interface HttpUserService {

    @GetMapping("/users/mail/{id}")
    UserResponseMailDTO getUserMailById(@PathVariable String id);

}
