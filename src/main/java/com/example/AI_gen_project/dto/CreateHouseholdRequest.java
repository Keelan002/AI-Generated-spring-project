package com.example.AI_gen_project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;

public record CreateHouseholdRequest(
        @NotBlank(message = "Eircode is required")
        @Pattern(regexp = "[A-Z0-9]{8}", message = "Invalid eircode format")
        String eircode,

        @NotNull(message = "Number of occupants is required")
        @Min(value = 0, message = "Number of occupants must be non-negative")
        Integer numberOfOccupants,

        @NotNull(message = "Maximum occupants is required")
        @Positive(message = "Maximum occupants must be positive")
        Integer maxNumberOfOccupants,

        @NotNull(message = "Owner occupied status is required")
        Boolean ownerOccupied
) {}

