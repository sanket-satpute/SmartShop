package com.smartshop.UserService.api_response.dto;

import com.smartshop.UserService.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDTO {

    @NotBlank(message = "Name is required")
    @Schema(description = "The name of the user", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Schema(description = "the email should be in active mode", example = "example@mail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Schema(description = "the password should be atleast 8 characters long it should atleast contain 1 uppercase 1 lowercase 1 numeric 1 special characters ", example = "P@ssw0rd", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    @Schema(description = "the role of the user", example = "CUSTOMER", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Role is required")
    private Role role;
}
