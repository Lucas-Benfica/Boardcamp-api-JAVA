package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RentalsDTO {

    @NotNull(message = "Price per day total is required.")
    @Positive(message = "Price per day must be greater than zero.")
    private Long customerId;

    @NotNull(message = "Price per day total is required.")
    @Positive(message = "Price per day must be greater than zero.")
    private Long gameId;

    @NotNull(message = "Days rented is required.")
    @Positive(message = "Days rented must be greater than zero.")
    private Long daysRented;

}
