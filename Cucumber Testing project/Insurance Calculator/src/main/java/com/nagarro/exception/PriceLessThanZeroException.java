package com.nagarro.exception;

public class PriceLessThanZeroException extends Exception {
    public PriceLessThanZeroException() {

    }
    public PriceLessThanZeroException(String message) {
        super(message);
    }
}
