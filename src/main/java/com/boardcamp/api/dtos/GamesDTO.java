package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GamesDTO {
    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Image is required.")
    private String image;

    @NotNull(message = "Stock total is required.")
    @Positive(message = "Stock total must be greater than zero.")
    private Long stockTotal;

    @NotNull(message = "Price per day is required.")
    @Positive(message = "Price per day must be greater than zero.")
    private Long pricePerDay;
}
