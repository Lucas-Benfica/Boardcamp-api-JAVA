package com.boardcamp.api.models;

import java.time.LocalDate;

import com.boardcamp.api.dtos.RentalsDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rentals")
public class RentalsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private CustomersModel customer;

    @ManyToOne
    @JoinColumn(name = "gameId")
    private GamesModel game;
    
    @Column(nullable = false)
    private LocalDate rentDate;
    
    @Column(nullable = false)
    private Long daysRented;
    
    @Column(nullable = true)
    private LocalDate returnDate;
    
    @Column(nullable = false)
    private Long originalPrice;
    
    @Column(nullable = false)
    private Long delayFee;

    public RentalsModel(RentalsDTO dto, CustomersModel customer, GamesModel game){
        this.customer = customer;
        this.game = game;
        this.rentDate = LocalDate.now();
        this.daysRented = dto.getDaysRented();
        this.returnDate = null;
        this.originalPrice = game.getPricePerDay() * dto.getDaysRented();
        this.delayFee = 0L;
    }
    
}
