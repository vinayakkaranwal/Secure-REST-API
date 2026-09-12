package com.vinayak.secure_rest_api.dto;

import com.vinayak.secure_rest_api.entities.enums.Permissions;
import com.vinayak.secure_rest_api.entities.enums.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
public class SignupDTO {

    @NotBlank(message = "Name cannot be empty or whitespace")
    private String username;

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private Set<Roles> roles;
    private Set<Permissions> permissions;
}
