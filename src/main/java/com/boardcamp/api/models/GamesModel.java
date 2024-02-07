package com.boardcamp.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GamesModel {
    
    private Long id;
    private String name;
    private String image;
    private Long stockTotal;
    private Long pricePerDay;

}
