package com.vinayak.secure_rest_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskDTO {

    @Min(value = 1, message = "User ID must be greater than or equal to 1")
    private Long id;

    @NotBlank(message = "Cannot be empty or whitespace")
    private String title;

    private String description;

    private String status;

    @NotNull(message = "Cannot be empty")
    private Long user_id;
}
