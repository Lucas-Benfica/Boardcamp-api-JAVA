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
import com.boardcamp.api.exceptions.CpfConflictException;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.models.CustomersModel;
import com.boardcamp.api.repositories.CustomersRepository;
import com.boardcamp.api.services.CustomersService;

@SpringBootTest
class CustomersUnitTests {

	@InjectMocks
	private CustomersService customersService;

	@Mock
	private CustomersRepository customersRepository;
	
	@Test
	void givenRepeatedCPF_whenCreatingCustomer_thenThrowsError() {

		CustomersDTO customersDto = new CustomersDTO("teste", "11111111111");
		doReturn(true).when(customersRepository).existsByCpf(any());

		CpfConflictException exception = assertThrows(
			CpfConflictException.class,
			() -> customersService.save(customersDto)
		);

		assertNotNull(exception);
		assertEquals("This CPF already registered.", exception.getMessage());
		verify(customersRepository, times(1)).existsByCpf(any());
		verify(customersRepository, times(0)).save(any());
	
	}

	@Test
	void givenWrongCustomerId_whenGettingCustomer_thenThrowsError() {

		doReturn(Optional.empty()).when(customersRepository).findById(1L);

		CustomerNotFoundException exception = assertThrows(
			CustomerNotFoundException.class,
			() -> customersService.findCustomerById(1L)
		);

		assertNotNull(exception);
		assertEquals("Customer not found.", exception.getMessage());
		verify(customersRepository, times(1)).findById(any());
	
	}

	@Test
	void givenValidCustomer_whenCreatingCustomer_thenCreatesCustomer(){

		CustomersDTO customersDTO = new CustomersDTO("teste", "11111111111");
		CustomersModel customer = new CustomersModel(customersDTO);
		
		doReturn(false).when(customersRepository).existsByCpf(any());
		doReturn(customer).when(customersRepository).save(any());

		//when
		CustomersModel result = customersService.save(customersDTO);

		verify(customersRepository, times(1)).existsByCpf(any());
		verify(customersRepository, times(1)).save(any());
		assertEquals(customer, result);
	}

}
