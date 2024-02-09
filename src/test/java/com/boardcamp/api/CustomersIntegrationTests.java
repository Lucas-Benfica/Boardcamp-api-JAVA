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
import com.boardcamp.api.models.CustomersModel;
import com.boardcamp.api.repositories.CustomersRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CustomersIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private CustomersRepository customersRepository;

    @BeforeEach
    @AfterEach
    void cleanDB(){
        customersRepository.deleteAll();
    }

    @Test
    void givenValidCustomer_whenCreatingCustomer_thenCreatesCustomer(){
        CustomersDTO customersDTO = new CustomersDTO("teste", "11111111111");

        HttpEntity<CustomersDTO> body = new HttpEntity<>(customersDTO);

        ResponseEntity<CustomersModel> response = restTemplate.exchange(
            "/customers",
            HttpMethod.POST,
            body,
            CustomersModel.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, customersRepository.count());

    }


}
