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

import com.boardcamp.api.dtos.GamesDTO;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.repositories.GamesRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GamesIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private GamesRepository gamesRepository;

    @BeforeEach
    @AfterEach
    void cleanDB(){
        gamesRepository.deleteAll();
    }

    @Test
    void givenValidGame_whenCreatingGame_thenCreatesGame(){
        GamesDTO gamesDTO = new GamesDTO("teste", "teste.img.jpg", 2L, 1000L);
        HttpEntity<GamesDTO> body = new HttpEntity<>(gamesDTO);

        ResponseEntity<GamesModel> response = restTemplate.exchange(
            "/games",
            HttpMethod.POST,
            body,
            GamesModel.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, gamesRepository.count());
    }

    @Test
    void givenRepeatedName_whenCreatingGame_thenThrowsError(){

        GamesDTO gamesDTO = new GamesDTO("teste", "teste.img.jpg", 2L, 1000L);

        GamesModel game = new GamesModel(gamesDTO);
        gamesRepository.save(game);

        GamesDTO gamesDTO2 = new GamesDTO("teste", "teste.imgj.jpg", 2L, 1000L);

        HttpEntity<GamesDTO> body = new HttpEntity<>(gamesDTO2);

        ResponseEntity<String> response = restTemplate.exchange(
            "/games",
            HttpMethod.POST,
            body,
            String.class
        );

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(1, gamesRepository.count());
    }

}
