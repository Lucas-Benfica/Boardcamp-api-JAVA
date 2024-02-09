package com.boardcamp.api.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentalsDTO;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.exceptions.GameNotFoundException;
import com.boardcamp.api.exceptions.NoStockException;
import com.boardcamp.api.exceptions.RentalAlreadyFinishedException;
import com.boardcamp.api.exceptions.RentalNotFoundException;
import com.boardcamp.api.models.CustomersModel;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.models.RentalsModel;
import com.boardcamp.api.repositories.CustomersRepository;
import com.boardcamp.api.repositories.GamesRepository;
import com.boardcamp.api.repositories.RentalsRepository;

@Service
public class RentalsService {
    
    final RentalsRepository rentalsRepository;
    final CustomersRepository customersRepository;
    final GamesRepository gamesRepository;

    RentalsService(
        RentalsRepository rentalsRepository, 
        CustomersRepository customersRepository,
        GamesRepository gamesRepository
        ){
        this.rentalsRepository = rentalsRepository;
        this.customersRepository = customersRepository;
        this.gamesRepository = gamesRepository;
    }

    public RentalsModel save(RentalsDTO body){
        CustomersModel customer = customersRepository.findById(body.getCustomerId()).orElseThrow(
            () -> new CustomerNotFoundException("Customer not found.")
        );

        GamesModel game = gamesRepository.findById(body.getGameId()).orElseThrow(
            () -> new GameNotFoundException("Game not found.")
        );

        List<RentalsModel> allRentalsForThisGame = rentalsRepository.findAllByGameId(body.getGameId());

        if(allRentalsForThisGame.size() >= game.getStockTotal()){
            throw new NoStockException("This game's stock is empty.");
        }

        RentalsModel rental = new RentalsModel(body, customer, game);

        return rentalsRepository.save(rental);
    }

    public List<RentalsModel> findAll(){
        return rentalsRepository.findAll();
    }

    public RentalsModel finish(Long id){
        RentalsModel rental = rentalsRepository.findById(id).orElseThrow(
            () -> new RentalNotFoundException("Rental not found.")
        );

        if(rental.getReturnDate() != null) throw new RentalAlreadyFinishedException("Rental already finished.");
        
        rental.setReturnDate(LocalDate.now());
        
        LocalDate rentDate = rental.getRentDate();
        LocalDate returnDate = rental.getReturnDate();

        Long daysWithGame = ChronoUnit.DAYS.between(rentDate, returnDate);
        Long daysRented = rental.getDaysRented();

        if(daysWithGame > daysRented) {
            Long extraDays = daysWithGame - daysRented;
            Long pricePerDay = rental.getOriginalPrice() / daysRented;

            rental.setDelayFee(extraDays * pricePerDay);
        }

        return rentalsRepository.save(rental);
    }
}
