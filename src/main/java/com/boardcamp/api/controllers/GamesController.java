package com.boardcamp.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.GamesDTO;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.services.GamesService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/games")
public class GamesController {
    
    final GamesService gamesService;

    GamesController(GamesService gamesService){
        this.gamesService = gamesService;
    }

    @PostMapping
    public ResponseEntity<GamesModel> createGame(@RequestBody @Valid GamesDTO body) {
        
        GamesModel game = gamesService.save(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(game);

    }
    

}
