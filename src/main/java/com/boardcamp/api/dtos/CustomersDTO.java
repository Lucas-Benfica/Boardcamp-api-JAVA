package com.boardcamp.api.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomersDTO {
    @NotBlank(message = "Name is required.")
    private String name;

    @NotNull
    @Size(min = 11, max = 11, message = "Provide a CPF in the '00000000000' format." )
    @Digits(integer = 11, fraction = 0, message = "CPF must be numeric only.")
    private String cpf;
}
