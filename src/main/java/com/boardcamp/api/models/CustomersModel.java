package com.boardcamp.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomersModel {
    
    private Long id;
    private String name;
    private String cpf;

}
