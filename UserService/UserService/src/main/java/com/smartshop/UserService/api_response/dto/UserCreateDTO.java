package com.smartshop.UserService.api_response.dto;

import com.smartshop.UserService.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDTO {
    private String name;
    private String email;
    private String password;
    private Role role;
}
