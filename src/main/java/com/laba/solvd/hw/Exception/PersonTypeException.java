package com.laba.solvd.hw.Exception;

public class PersonTypeException extends Exception {
    public PersonTypeException(String message) {
        super(message);
    }

    public PersonTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}