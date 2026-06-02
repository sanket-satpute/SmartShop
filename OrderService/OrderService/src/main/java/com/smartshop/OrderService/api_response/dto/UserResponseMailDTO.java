package com.smartshop.OrderService.api_response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserResponseMailDTO {
    private String id;
    private String name;
    private String email;
}
