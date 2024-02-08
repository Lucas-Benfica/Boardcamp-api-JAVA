package com.boardcamp.api.exceptions;

public class CpfConflictException extends RuntimeException{
    public CpfConflictException(String message){
        super(message);
    }
}
