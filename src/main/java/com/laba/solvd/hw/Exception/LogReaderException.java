package com.laba.solvd.hw.Exception;

public class LogReaderException extends Exception {
    public LogReaderException(String message) {
        super(message);
    }

    public LogReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}