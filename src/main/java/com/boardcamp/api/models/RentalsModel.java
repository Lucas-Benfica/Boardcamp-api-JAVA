package com.boardcamp.api.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalsModel {
    private Long id;
    private Long customerId;
    private Long gameId;
    private LocalDate rentDate;
    private Long daysRented;
    private LocalDate returnDate;
    private Long originalPrice;
    private Long delayFee;
}
