package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.dtos.CustomersDTO;
import com.boardcamp.api.dtos.GamesDTO;
import com.boardcamp.api.dtos.RentalsDTO;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.exceptions.GameNotFoundException;
import com.boardcamp.api.models.CustomersModel;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.models.RentalsModel;
import com.boardcamp.api.repositories.CustomersRepository;
import com.boardcamp.api.repositories.GamesRepository;
import com.boardcamp.api.repositories.RentalsRepository;
import com.boardcamp.api.services.RentalsService;

@SpringBootTest
class RentalsUnitTests {

    @InjectMocks
    private RentalsService rentalsService;

    @Mock
    private RentalsRepository rentalsRepository;
    @Mock
    private CustomersRepository customersRepository;
    @Mock
    private GamesRepository gamesRepository;

    @Test
    void givenValidRental_whenCreatingRental_thenCreatesRental() {

        CustomersDTO customersDTO = new CustomersDTO("teste", "11111111111");
        CustomersModel customer = new CustomersModel(customersDTO);
        GamesDTO gamesDTO = new GamesDTO("Teste", "teste.img.jpg", 2L, 1000L);
        GamesModel game = new GamesModel(gamesDTO);
        RentalsDTO rentalsDTO = new RentalsDTO(customer.getId(), game.getId(), 2L);
        RentalsModel rental = new RentalsModel(rentalsDTO, customer, game);
        
        doReturn(Optional.of(customer)).when(customersRepository).findById(customer.getId());
        doReturn(Optional.of(game)).when(gamesRepository).findById(any());
        doReturn(rental).when(rentalsRepository).save(any());

        RentalsModel result = rentalsService.save(rentalsDTO);

        verify(customersRepository, times(1)).findById(any());
        verify(gamesRepository, times(1)).findById(any());
        verify(rentalsRepository, times(1)).save(any());
        assertEquals(rental, result);

    }

    @Test
    void givenWrongCustomerId_whenCreatingRental_thenThrowsError() {
        CustomersDTO customersDTO = new CustomersDTO("teste", "11111111111");
        CustomersModel customer = new CustomersModel(customersDTO);
        RentalsDTO rentalsDTO = new RentalsDTO(1L, 1L, 2L);
        
        doReturn(Optional.empty()).when(customersRepository).findById(customer.getId());

        CustomerNotFoundException exception = assertThrows(
			CustomerNotFoundException.class,
			() -> rentalsService.save(rentalsDTO)
		);

        verify(customersRepository, times(1)).findById(any());
        verify(gamesRepository, times(0)).findById(any());
        verify(rentalsRepository, times(0)).save(any());
        assertNotNull(exception);
		assertEquals("Customer not found.", exception.getMessage());
    }

    @Test
    void givenWrongGameId_whenCreatingRental_thenThrowsError() {
        CustomersDTO customersDTO = new CustomersDTO("teste", "11111111111");
        CustomersModel customer = new CustomersModel(customersDTO);
        GamesDTO gamesDTO = new GamesDTO("Teste", "teste.img.jpg", 2L, 1000L);
        GamesModel game = new GamesModel(gamesDTO);
        RentalsDTO rentalsDTO = new RentalsDTO(customer.getId(), game.getId(), 2L);
        
        doReturn(Optional.of(customer)).when(customersRepository).findById(customer.getId());
        doReturn(Optional.empty()).when(gamesRepository).findById(any());

        GameNotFoundException exception = assertThrows(
			GameNotFoundException.class,
			() -> rentalsService.save(rentalsDTO)
		);

        verify(customersRepository, times(1)).findById(any());
        verify(gamesRepository, times(1)).findById(any());
        verify(rentalsRepository, times(0)).save(any());
        assertNotNull(exception);
		assertEquals("Game not found.", exception.getMessage());
    }
}
