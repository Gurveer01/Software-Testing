package com.nagarro.exception;

public class InvalidTypeException extends Exception{
    public InvalidTypeException() {
    }

    public InvalidTypeException(String message) {
        super(message);
    }
}
