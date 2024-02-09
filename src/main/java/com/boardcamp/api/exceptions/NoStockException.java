package com.boardcamp.api.exceptions;

public class NoStockException extends RuntimeException {
    public NoStockException(String message){
        super(message);
    }
}
