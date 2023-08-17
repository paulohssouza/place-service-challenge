package br.com.phss.placeservicechallenge.api;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record PlaceRequest(
        @NotBlank
        String name,
        @NotBlank
        String city,
        @NotBlank
        String state
) {
}
