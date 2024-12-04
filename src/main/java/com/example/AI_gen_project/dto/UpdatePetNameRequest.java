package com.example.AI_gen_project.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePetNameRequest(
        @NotBlank(message = "Name is required")
        String name
) {}
