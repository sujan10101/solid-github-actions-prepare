package com.example.solidprinciple.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// Used for POST and PUT — title is required
@Data
public class TodoRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private boolean completed;
}
