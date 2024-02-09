package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.boardcamp.api.dtos.CustomersDTO;
import com.boardcamp.api.dtos.GamesDTO;
import com.boardcamp.api.dtos.RentalsDTO;
import com.boardcamp.api.models.CustomersModel;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.models.RentalsModel;
import com.boardcamp.api.repositories.CustomersRepository;
import com.boardcamp.api.repositories.GamesRepository;
import com.boardcamp.api.repositories.RentalsRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class RentalsIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private RentalsRepository rentalsRepository;
    @Autowired
    private GamesRepository gamesRepository;
    @Autowired
    private CustomersRepository customersRepository;

    @BeforeEach
    @AfterEach
    void cleanDB(){
        rentalsRepository.deleteAll();
        gamesRepository.deleteAll();
        customersRepository.deleteAll();
    }

    @Test
    void givenValidRental_whenCreatingRental_thenCreatesRental(){
        CustomersDTO customersDTO = new CustomersDTO("teste", "11111111111");
        CustomersModel customerModel = new CustomersModel(customersDTO);
        CustomersModel customer = customersRepository.save(customerModel);
        GamesDTO gamesDTO = new GamesDTO("Teste", "teste.img.jpg", 2L, 1000L);
        GamesModel gameModel = new GamesModel(gamesDTO);
        GamesModel game = gamesRepository.save(gameModel);
        RentalsDTO rentalsDTO = new RentalsDTO(customer.getId(), game.getId(), 2L);

        HttpEntity<RentalsDTO> body = new HttpEntity<>(rentalsDTO);

        ResponseEntity<RentalsModel> response = restTemplate.exchange(
            "/rentals",
            HttpMethod.POST,
            body,
            RentalsModel.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, customersRepository.count());
    }

    @Test
    void givenWrongCustomerId_whenCreatingRental_thenThrowsError(){
        CustomersDTO customersDTO = new CustomersDTO("teste", "11111111111");
        CustomersModel customerModel = new CustomersModel(customersDTO);
        CustomersModel customer = customersRepository.save(customerModel);
        GamesDTO gamesDTO = new GamesDTO("Teste", "teste.img.jpg", 2L, 1000L);
        GamesModel gameModel = new GamesModel(gamesDTO);
        GamesModel game = gamesRepository.save(gameModel);
        RentalsDTO rentalsDTO = new RentalsDTO(customer.getId() + 1, game.getId(), 2L);

        HttpEntity<RentalsDTO> body = new HttpEntity<>(rentalsDTO);

        ResponseEntity<String> response = restTemplate.exchange(
            "/rentals",
            HttpMethod.POST,
            body,
            String.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(1, customersRepository.count());
    }

    @Test
    void givenWrongGameId_whenCreatingRental_thenThrowsError(){
        CustomersDTO customersDTO = new CustomersDTO("teste", "11111111111");
        CustomersModel customerModel = new CustomersModel(customersDTO);
        CustomersModel customer = customersRepository.save(customerModel);
        GamesDTO gamesDTO = new GamesDTO("Teste", "teste.img.jpg", 2L, 1000L);
        GamesModel gameModel = new GamesModel(gamesDTO);
        GamesModel game = gamesRepository.save(gameModel);
        RentalsDTO rentalsDTO = new RentalsDTO(customer.getId(), game.getId() + 1, 2L);

        HttpEntity<RentalsDTO> body = new HttpEntity<>(rentalsDTO);

        ResponseEntity<String> response = restTemplate.exchange(
            "/rentals",
            HttpMethod.POST,
            body,
            String.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(1, customersRepository.count());
    }
}
