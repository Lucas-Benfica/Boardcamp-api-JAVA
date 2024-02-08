package com.boardcamp.api.services;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.CustomersDTO;
import com.boardcamp.api.exceptions.CpfConflictException;
import com.boardcamp.api.exceptions.CustomerNotFound;
import com.boardcamp.api.models.CustomersModel;
import com.boardcamp.api.repositories.CustomersRepository;

@Service
public class CustomersService {

    final CustomersRepository customersRepository;

    CustomersService(CustomersRepository customersRepository){
        this.customersRepository = customersRepository;
    }

    public CustomersModel save(CustomersDTO body){
        CustomersModel customer = new CustomersModel(body);

        boolean cpfExists = customersRepository.existsByCpf(customer.getCpf());

        if(cpfExists) throw new CpfConflictException("This CPF already registered.");

        return customersRepository.save(customer);
    }
    
    public CustomersModel findCustomerById(Long id){
        return customersRepository.findById(id).orElseThrow(
            () -> new CustomerNotFound("Customer not found.")
        );
    }
}
