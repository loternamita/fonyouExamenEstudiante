package com.fonyou.pruebatecnica.examen.exception;

public class CustomServiceException extends RuntimeException{
    public CustomServiceException(String message){
        super(message);
    }
}
