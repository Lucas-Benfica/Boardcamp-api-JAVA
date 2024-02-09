package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.dtos.GamesDTO;
import com.boardcamp.api.exceptions.GamesConflictException;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.repositories.GamesRepository;
import com.boardcamp.api.services.GamesService;

@SpringBootTest
class GamesUnitTests {
    @InjectMocks
    private GamesService gamesService;

    @Mock
    private GamesRepository gamesRepository;

    @Test
    void givenValidGame_whenCreatingGame_thenCreatesGame(){

        GamesDTO gamesDTO = new GamesDTO("Teste", "teste.img.jpg", 2L, 1000L);
        GamesModel game = new GamesModel(gamesDTO);
        doReturn(false).when(gamesRepository).existsByName(any());
        doReturn(game).when(gamesRepository).save(any());
        
        GamesModel result = gamesService.save(gamesDTO);

        verify(gamesRepository, times(1)).existsByName(any());
        verify(gamesRepository, times(1)).save(any());
        assertEquals(game, result);

    }

    @Test
    void givenRepeatedName_whenCreatingGame_thenThrowsError(){

        GamesDTO gamesDTO = new GamesDTO("Teste", "teste.img.jpg", 2L, 1000L);
        doReturn(true).when(gamesRepository).existsByName(any());

        GamesConflictException exception = assertThrows(
            GamesConflictException.class, () -> gamesService.save(gamesDTO)
        );
        

        verify(gamesRepository, times(1)).existsByName(any());
        verify(gamesRepository, times(0)).save(any());
        assertNotNull(exception);
		assertEquals("This game already exists.", exception.getMessage());
    }
}
