package com.boardcamp.api.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


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

    @GetMapping
    public ResponseEntity<List<RentalsModel>> getAllRentals() {
        return ResponseEntity.status(HttpStatus.OK).body(rentalsService.findAll());
    }

    @PutMapping("/{id}/return")
    public RentalsModel putMethodName(@PathVariable Long id) {        
        return rentalsService.finish(id);
    }
    
}
