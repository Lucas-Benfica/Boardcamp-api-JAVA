package com.boardcamp.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.RentalsDTO;
import com.boardcamp.api.models.RentalsModel;
import com.boardcamp.api.services.RentalsService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rentals")
public class RentalsController {
    
    final RentalsService rentalsService;

    RentalsController(RentalsService rentalsService){
        this.rentalsService = rentalsService;
    }

    @PostMapping
    public ResponseEntity<RentalsModel> createRental(@RequestBody @Valid RentalsDTO body) {
        RentalsModel rental = rentalsService.save(body);        
        return ResponseEntity.status(HttpStatus.CREATED).body(rental);
    }
    

}
