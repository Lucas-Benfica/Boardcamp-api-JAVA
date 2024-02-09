package com.boardcamp.api.services;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.CustomersDTO;
import com.boardcamp.api.exceptions.CpfConflictException;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.models.CustomersModel;
import com.boardcamp.api.repositories.CustomersRepository;

@Service
public class CustomersService {

    final CustomersRepository customersRepository;

    CustomersService(CustomersRepository customersRepository){
        this.customersRepository = customersRepository;
    }

    public CustomersModel save(CustomersDTO body){
        boolean cpfExists = customersRepository.existsByCpf(body.getCpf());
        
        if(cpfExists) throw new CpfConflictException("This CPF already registered.");
        
        CustomersModel customer = new CustomersModel(body);

        return customersRepository.save(customer);
    }
    
    public CustomersModel findCustomerById(Long id){
        return customersRepository.findById(id).orElseThrow(
            () -> new CustomerNotFoundException("Customer not found.")
        );
    }
}
