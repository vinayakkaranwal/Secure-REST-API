package com.vinayak.secure_rest_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    @Min(value = 1, message = "User ID must be greater than or equal to 1")
    private Long id;

    @NotBlank(message = "Cannot be empty or whitespace")
    private String accessToken;

    @NotBlank(message = "Cannot be empty or whitespace")
    private String refreshToken;
}
