package com.vinayak.secure_rest_api.dto;

import com.vinayak.secure_rest_api.entities.enums.Permissions;
import com.vinayak.secure_rest_api.entities.enums.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Data
public class UserDTO { //SignupResponseDTO

    @Min(value = 1, message = "User ID must be greater than or equal to 1")
    private Long id;

    @NotBlank(message = "Cannot be empty or whitespace")
    private String username;

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    private String email;

    private Set<Roles> roles;
    private Set<Permissions> permissions;

}
