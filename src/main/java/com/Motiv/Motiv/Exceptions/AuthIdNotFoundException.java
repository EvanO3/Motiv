package com.Motiv.Motiv.Exceptions;

public class AuthIdNotFoundException extends RuntimeException{
    public AuthIdNotFoundException(String message){
        super(message);
    }
}
