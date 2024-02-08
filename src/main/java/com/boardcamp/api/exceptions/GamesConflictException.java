package com.boardcamp.api.exceptions;

public class GamesConflictException extends RuntimeException {
    public GamesConflictException(String message){
        super(message);
    }
}
