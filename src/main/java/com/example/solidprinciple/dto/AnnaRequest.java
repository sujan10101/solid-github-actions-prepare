package com.example.solidprinciple.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnnaRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String country;
}
