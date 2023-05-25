package com.laba.solvd.hw.Exception;

public class JailFullException extends Exception {
    public JailFullException() {
        super("This jail is at full capacity! The inmate will be kept in holding for now.");
    }
}
