package com.boardcamp.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.GamesDTO;
import com.boardcamp.api.exceptions.GamesConflictException;
import com.boardcamp.api.models.GamesModel;
import com.boardcamp.api.repositories.GamesRepository;

@Service
public class GamesService {

    final GamesRepository gamesRepository;

    GamesService(GamesRepository gamesRepository){
        this.gamesRepository = gamesRepository;
    }

    public GamesModel save(GamesDTO body){
        GamesModel game = new GamesModel(body);
        
        boolean gameExists = gamesRepository.existsByName(game.getName());

        if(gameExists) throw new GamesConflictException("This game already exists.");
        
        return gamesRepository.save(game);
    }

    public List<GamesModel> findAll(){
        return gamesRepository.findAll();
    }


}
